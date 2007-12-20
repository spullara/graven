package com.googlecode.graven;

testdependency = {dependencies, gid, aid, v ->
    dependencies.dependency {
        groupId gid
        artifactId aid
        version v
        scope "test"
    }
}

dependency = {dependencies, gid, aid, v ->
    dependencies.dependency {
        groupId gid
        artifactId aid
        version v
    }
}

compiler = {plugins, version ->
    plugins.plugin {
        groupId "org.apache.maven.plugins"
        artifactId "maven-compiler-plugin"
        configuration {
            source version
            target version
        }
    }
}

executableJar = {plugins, mainclassname ->
    plugins.plugin {
        artifactId "maven-assembly-plugin"
        configuration {
            descriptorRefs {
                descriptorRef "jar-with-dependencies"
            }
            archive {
                manifest {
                    mainClass mainclassname
                }
            }
        }
        executions {
            execution {
                id "make-assembly"
                phase "package"
                goals {
                    goal "attached"
                }
            }
        }
    }
}

repository = {parent, _id, _name, _url ->
    parent.repository {
        id _id
        name _name
        url _url
    }
}

groovy = { plugins ->
    plugins.plugin {
        artifactId "maven-antrun-plugin"
        executions {
            execution {
                id "compile"
                phase "compile"
                configuration {
                    tasks {
                        taskdef (name:"groovyc", classname:"org.codehaus.groovy.ant.Groovyc") {
                            classpath(refid:"maven.compile.classpath")
                        }
                        mkdir (dir:'${project.build.outputDirectory}')
                        groovyc (destdir:'${project.build.outputDirectory}',
                                srcdir:'${basedir}/src/main/groovy',
                                listfiles:"true") {
                            classpath(refid:"maven.compile.classpath")
                        }
                    }
                }
                goals {
                    goal "run"
                }
            }
            execution {
                id "test-compile"
                phase "test-compile"
                configuration {
                    tasks {
                        taskdef (name:"groovyc", classname:"org.codehaus.groovy.ant.Groovyc") {
                            classpath(refid:"maven.compile.classpath")
                        }
                        mkdir (dir:'${project.build.testOutputDirectory}')
                        groovyc (destdir:'${project.build.testOutputDirectory}',
                                srcdir:'${basedir}/src/test/groovy',
                                listfiles:"true") {
                            classpath(refid:"maven.compile.classpath")
                        }
                    }
                }
                goals {
                    goal "run"
                }
            }
        }
    }
}