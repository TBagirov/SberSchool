package org.bagirov.model;

public class Child extends Parent {

    static {
        System.out.println("Child:static 1");
    }

    {
        System.out.println("Child:instance 1");
    }

    static{
        System.out.println("Child:static 2");
    }

    public Child(){ System.out.println("Child:constructor"); }

    public Child(String Name) {
        super(Name);
        System.out.println("Child:name-constructor");
    }

    {
        System.out.println("Child:instance 2");
    }
}
