package com.googlecode.graven;


import groovy.xml.MarkupBuilder

GroovyShell shell = new GroovyShell()
String filename = args.length == 0 ? "pom.groovy" : args[0]
Script script = shell.parse(new File(filename))
MarkupBuilder mb = new MarkupBuilder()
def binding = new Binding()
binding.setProperty "project", { pom ->
    mb.project {
        pom.owner = script
        pom.delegate = mb
        pom()
    }
}
def includeClass = {clazz ->
    def include = clazz.newInstance()
    include.run()
    include.binding.properties.variables.each {
        binding.setVariable it.key, it.value.curry(mb)
    }
    println binding.properties.variables
}
includeClass(Pom)
binding.setProperty "include", includeClass
script.binding = binding
script.run();
