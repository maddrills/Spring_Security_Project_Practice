package com.loginpage.basicLoginSignUp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "users")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "name")
    String name;
    @Column(name = "age")
    int age;
    @Column(name = "email")
    String email;
    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH,
            }
            //cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "users_authorities",
            joinColumns = @JoinColumn(name = "id_users"),
            inverseJoinColumns = @JoinColumn(name = "id_authorities")
    )
    Collection<Authorities> authorities;

    Person(){}

    public Person(String name, int age, String email, String password) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
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

    public String getPassword() {
        return password;
    }

    public Collection<Authorities> getAuthorities() {
        return authorities;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    convenience method
    public void addRoles(Authorities authorities){
        if(this.authorities == null){
//            i want to maintain order of insertion and no duplicate values
            this.authorities = new LinkedHashSet<>();
        }
        this.authorities.add(authorities);
    }
}
