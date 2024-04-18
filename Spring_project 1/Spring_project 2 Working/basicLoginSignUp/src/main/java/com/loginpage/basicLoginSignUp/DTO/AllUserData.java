package com.loginpage.basicLoginSignUp.DTO;

import com.loginpage.basicLoginSignUp.entity.Authorities;
import jakarta.persistence.Column;

import java.util.Collection;
import java.util.HashSet;

public class AllUserData {

    String name;

    int age;

    String email;

    final Collection<String> auths = new HashSet<>();


    public AllUserData(Collection<Authorities> auths, String email, int age, String name) {

        auths.forEach(a -> this.auths.add(a.getAuthority()));
        this.email = email;
        this.age = age;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<String> getAuths() {
        return auths;
    }

    @Override
    public String toString() {
        return "AllUserData{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", auths=" + auths +
                '}';
    }
}
