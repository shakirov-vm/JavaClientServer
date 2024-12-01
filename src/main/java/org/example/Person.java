package org.example;

import java.io.Serializable;

public class Person implements Serializable {

    int age;
    String name;

    public Person(int age_, String name_) {
        age = age_;
        name = name_;
    }
    public void Hello() {
        System.out.println("Hello, my name is " + name + " and i am " + age);
    }
}
