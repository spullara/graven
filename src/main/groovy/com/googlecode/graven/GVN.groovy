package com.googlecode.graven;


import groovy.xml.MarkupBuilder

GroovyShell shell = new GroovyShell()
Script script = shell.parse(new File("pom.groovy"))
def fw = new FileWriter("pom.xml");
MarkupBuilder mb = new MarkupBuilder(fw)
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
}
includeClass(Pom)
binding.setProperty "include", includeClass
script.binding = binding
script.run();
fw.close();
def list = ["mvn"]
list.addAll(args as List)
new ProcessBuilder(list).redirectErrorStream(true).start().in.eachLine {
    println it
}
