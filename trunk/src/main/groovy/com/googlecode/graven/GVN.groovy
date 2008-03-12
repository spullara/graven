package com.googlecode.graven;

import groovy.xml.MarkupBuilder

// Read the pom.groovy script
GroovyShell shell = new GroovyShell()
Script script = shell.parse(new File("pom.groovy"))

// Write out a pom.xml
def fw = new FileWriter("pom.xml");

// Setup the markup builder
MarkupBuilder mb = new MarkupBuilder(fw)

// Bind the markup builder into the script
def binding = new Binding()
binding.setProperty "project", { pom ->
    mb.project {
        pom.owner = script
        pom.delegate = mb
        pom()
    }
}

// Include all of the variables on the included script in our binding
def includeClass = {clazz ->
    def include = clazz.newInstance()
    include.run()
    include.binding.properties.variables.each {
        binding.setVariable it.key, it.value.curry(mb)
    }
}

// Standard library
includeClass(Pom)

// Add the includer to the binding
binding.setProperty "include", includeClass

// Set the script binding and execute
script.binding = binding
script.run();
fw.close();

// Excecute maven 2
def list = ["mvn"]
list.addAll(args as List)
new ProcessBuilder(list).redirectErrorStream(true).start().in.eachLine {
    println it
}