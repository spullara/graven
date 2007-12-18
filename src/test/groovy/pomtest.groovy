


modelVersion "4.0.0"
groupId "com.sampullara.dbmigrate"
artifactId "dbmigrate"
packaging "jar"
name "DB Migrate"
version "1.0-SNAPSHOT"
url "http://code.google.com/p/dbmigrate"
description "A database migration system for Java"

googlecode(delegate, "dbmigrate")
asl2(delegate)

distributionManagement {
    repository(delegate, "javarants", "libs-snapshot", "http://maven.javarants.com/artifactory/libs-snapshots")
}

repositories {
    repository(delegate, "javarants", "repo", "http://maven.javarants.com/artifactory/repo")
}

dependencies {
    testdependency(delegate, "junit", "junit", "4.0")
    testdependency(delegate, "com.h2database", "h2", "1.0.63")
    dependency(delegate, "com.sampullara.cli", "clie-parser", "1.0-SNAPSHOT")
    dependency(delegate, "groovy", "groovy-all", "1.1-rc-1")
}

build {
    plugins {
        compiler(delegate, "1.5")
        executableJar(delegate, "com.sampullara.db.Migrate")
    }
}
