project() {
  modelVersion('4.0.0')
  groupId('com.googlecode.graven')
  artifactId('graven')
  packaging('jar')
  name('Graven')
  version('1.0-SNAPSHOT')
  url('http://code.google.com/p/graven')
  description('Build your POM files in groovy')
  scm() {
    connection('scm:svn:http://graven.googlecode.com/svn/trunk')
    developerConnection('scm:svn:https://graven.googlecode.com/svn/trunk')
    url('http://graven.googlecode.com/svn')
  }
  issueManagement() {
    system('Google Code')
    url('http://code.google.com/p/graven/issues/list')
  }
  licenses() {
    license() {
      name('The Apache Software License, Version 2.0')
      url('http://www.apache.org/licenses/LICENSE-2.0.txt')
      distribution('repo')
    }
  }
  distributionManagement() {
    repository() {
      id('javarants')
      name('libs-snapshot')
      url('http://maven.javarants.com/artifactory/libs-snapshots')
    }
  }
  repositories() {
    repository() {
      id('javarants')
      name('repo')
      url('http://maven.javarants.com/artifactory/repo')
    }
  }
  dependencies() {
    dependency() {
      groupId('org.codehaus.groovy')
      artifactId('groovy-all')
      version('1.5.1')
    }
  }
  build() {
    plugins() {
      plugin() {
        groupId('org.apache.maven.plugins')
        artifactId('maven-compiler-plugin')
        configuration() {
          source('1.5')
          target('1.5')
        }
      }
      plugin() {
        artifactId('maven-assembly-plugin')
        configuration() {
          descriptorRefs() {
            descriptorRef('jar-with-dependencies')
          }
          archive() {
            manifest() {
              mainClass('com.googlecode.graven.GVN')
            }
          }
        }
        executions() {
          execution() {
            id('make-assembly')
            phase('package')
            goals() {
              goal('attached')
            }
          }
        }
      }
      plugin() {
        artifactId('maven-antrun-plugin')
        executions() {
          execution() {
            id('compile')
            phase('compile')
            configuration() {
              tasks() {
                taskdef(classname:'org.codehaus.groovy.ant.Groovyc', name:'groovyc') {
                  classpath(refid:'maven.compile.classpath')
                }
                mkdir(dir:'${project.build.outputDirectory}')
                groovyc(destdir:'${project.build.outputDirectory}', listfiles:'true', srcdir:'${basedir}/src/main/groovy') {
                  classpath(refid:'maven.compile.classpath')
                }
              }
            }
            goals() {
              goal('run')
            }
          }
          execution() {
            id('test-compile')
            phase('test-compile')
            configuration() {
              tasks() {
                taskdef(classname:'org.codehaus.groovy.ant.Groovyc', name:'groovyc') {
                  classpath(refid:'maven.compile.classpath')
                }
                mkdir(dir:'${project.build.testOutputDirectory}')
                groovyc(destdir:'${project.build.testOutputDirectory}', listfiles:'true', srcdir:'${basedir}/src/test/groovy') {
                  classpath(refid:'maven.compile.classpath')
                }
              }
            }
            goals() {
              goal('run')
            }
          }
        }
      }
    }
  }
}
