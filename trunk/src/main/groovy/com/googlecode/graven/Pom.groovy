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
                phase "pacakge"
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
