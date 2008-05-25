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

groovydeps = { dependencies ->
	dependencies.dependency {
		groupId "org.codehaus.groovy.maven"
		artifactId "gmaven-mojo"
		version "1.0-rc-2"
	}
	dependencies.dependency {
		groupId "org.codehaus.groovy.maven.runtime"
		artifactId "gmaven-runtime-default"
		version "1.0-rc-2"
	}
}

groovy = { plugins -> 
	plugins.plugin {
		groupId "org.codehaus.groovy.maven"
		artifactId "gmaven-plugin"
		version "1.0-rc-2"
		executions {
			execution {
				goals {
					goal "compile"
					goal "testCompile"
				}
			}
		}
	}
}

antlr = {plugins, debugEnabled ->
    plugins.plugin {
        groupId "org.codehaus.mojo"
        artifactId "antlr3-maven-plugin"
        configuration {
            debug "${debugEnabled}"
        }
        executions {
            execution {
                goals {
                    goal "antlr"
                }
            }
        }
    }
}