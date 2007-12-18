import groovy.xml.MarkupBuilder

class Pom extends MarkupBuilder {
    Pom(binding) {
        super(new PrintWriter(System.out, true));
        binding.googlecode = {project, name ->
            project.scm {
                connection "scm:svn:http://${name}.googlecode.com/svn/trunk"
                developerConnection "scm:svn:https://${name}.googlecode.com/svn/trunk"
                url "http://${name}.googlecode.com/svn"
            }

            project.issueManagement {
                system "Google Code"
                url "http://code.google.com/p/${name}/issues/list"
            }
        }

        binding.asl2 = {project ->
            project.licenses {
                license {
                    name "The Apache Software License, Version 2.0"
                    url "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    distribution "repo"
                }
            }
        }

        binding.testdependency = {dependencies, gid, aid, v ->
            dependencies.dependency {
                groupId gid
                artifactId aid
                version v
                scope "test"
            }
        }

        binding.dependency = {dependencies, gid, aid, v ->
            dependencies.dependency {
                groupId gid
                artifactId aid
                version v
            }
        }

        binding.compiler = {plugins, version ->
            plugins.plugin {
                groupId "org.apache.maven.plugins"
                artifactId "maven-compiler-plugin"
                configuration {
                    source version
                    target version
                }
            }
        }

        binding.executableJar = {plugins, mainclassname ->
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
                        phase "pacakge"
                        goals {
                            goal "attached"
                        }
                    }
                }
            }
        }

        binding.repository = {parent, _id, _name, _url ->
            parent.repository {
                id _id
                name _name
                url _url
            }
        }


    }

}
