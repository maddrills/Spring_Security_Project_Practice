package com.loginpage.basicLoginSignUp.DTO;

import com.loginpage.basicLoginSignUp.entity.Authorities;
import jakarta.persistence.Column;

import java.util.Collection;
import java.util.HashSet;
public class AllUserData {

    String name;

    String password;

    int age;

    String email;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AllUserData{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
