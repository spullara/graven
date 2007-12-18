package com.sampullara.moovy;

import groovy.util.BuilderSupport;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * TODO: Edit this
 * <p/>
 * User: sam
 * Date: Dec 17, 2007
 * Time: 6:48:53 PM
 */
public class POMBuilder extends BuilderSupport {

    private Model model = new Model();

    protected void setParent(Object parent, Object child) {
        System.out.println(parent + " <= " + child);
    }

    @Override
    protected Object postNodeCompletion(Object parent, Object node) {
        setCurrent(parent);
        return node;
    }

    protected Object createNode(Object o) {
        String name = (String) o;
        System.out.println(name);
        if (name.equals("project")) {
            return model;
        }
        try {
            Class c = Class.forName("org.apache.maven.model." + name.substring(0, 1).toUpperCase() + name.substring(1));
            Object node = c.newInstance();
            setCurrent(node);
            return node;
        } catch (ClassNotFoundException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        }
    }

    protected Object createNode(Object name, Object value) {
        System.out.println(name + "=" + value);
        try {
            BeanInfo bi = Introspector.getBeanInfo(getCurrent().getClass());
            PropertyDescriptor[] pds = bi.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                if (name.equals(pd.getName())) {
                    pd.getWriteMethod().invoke(getCurrent(), value);
                }
            }
            return null;
        } catch (IntrospectionException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    protected Object createNode(Object name, Map attributes) {
        System.out.println(name + "=" + attributes);
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    protected Object createNode(Object name, Map attributes, Object value) {
        System.out.println(name + "=" + attributes + "," + value);
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String toString() {
        MavenXpp3Writer writer = new MavenXpp3Writer();
        StringWriter sw = new StringWriter();
        try {
            writer.write(sw, model);
        } catch (IOException e) {
            return "ERROR IN MODEL";
        }
        return sw.toString();
    }
}
