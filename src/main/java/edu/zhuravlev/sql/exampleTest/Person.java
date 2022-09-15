package edu.zhuravlev.sql.exampleTest;

import java.util.Objects;

public class Person {

    private int id;
    private String name;
    private String email;
    private String country;
    private String password;

    public Person() {};
    public Person(int id, String name, String email, String country, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.country = country;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return id == person.id && name.equals(person.name) && email.equals(person.email) && country.equals(person.country) && password.equals(person.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, country, password);
    }

    @Override
    public String toString() {
        return "Person = {id=" + id + ", name=" + name +
                ", email=" + email + ", country=" + country +
                ", password=" + password + "}";
    }
}
