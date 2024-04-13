package com.loginpage.basicLoginSignUp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "authorities")
public class Authorities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "authority")
    String authority;


    Authorities(){}

    public Authorities(String authority) {
        this.authority = authority;
    }

    public int getId() {
        return id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
