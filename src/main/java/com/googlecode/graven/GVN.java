package com.googlecode.graven;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.lang.Binding;
import groovy.xml.MarkupBuilder;

import java.io.File;
import java.io.IOException;

/**
 * TODO: Edit this
 * <p/>
 * User: sam
 * Date: Dec 17, 2007
 * Time: 9:43:59 PM
 */
public class GVN {
    public static void main(String[] args) throws IOException {
        GroovyShell shell = new GroovyShell();
        String filename = args.length == 0 ? "pom.groovy" : args[0];
        Script script = shell.parse(new File(filename));
        MarkupBuilder mb = new MarkupBuilder();
        Binding binding = new Binding();
        binding.setProperty("delegate", mb);
        script.run();
    }
}
