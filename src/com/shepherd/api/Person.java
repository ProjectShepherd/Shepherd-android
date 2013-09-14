package com.shepherd.api;

public class Person {
    public long id;

    public String firstName;
    public String middleName;
    public String lastName;
    public int age;
    public int height;
    public int weight;
    public String sex;
    public String eye;
    public String hair;
    public String race;
    public String description;
    public String photo;
    public String thumb;
    
    public Person(){
    }
    public Person(Person other){
    	Person p = new Person();
    	p.id = other.id;
    	p.firstName = other.firstName;
    	p.lastName = other.lastName;
    	p.description = other.description;
    }
}
