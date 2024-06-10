package com.demo.mssql.javaspringbootconnectmssqlsample.model;

import javax.persistence.*;

@Entity
@Table(name = "user_tbl")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private int age;

    @Column(name = "binary_data")
    private byte[] binaryData; // New binary column

    public User() {
    }

    public User(String name, int age, byte[] binaryData) {
        this.name = name;
        this.age = age;
        this.binaryData = binaryData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public byte[] getBinaryData() {
        return binaryData;
    }

    public void setBinaryData(byte[] binaryData) {
        this.binaryData = binaryData;
    }
}
