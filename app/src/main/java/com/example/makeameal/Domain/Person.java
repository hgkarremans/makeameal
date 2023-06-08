package com.example.makeameal.Domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Person implements Serializable {
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("emailAdress")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("phoneNumber")
    @Expose
    private String phone;
    @SerializedName("id")
    @Expose
    private int id;


    public Person(String firstName, String lastName, String email, String phone, String password, String city, String street) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.city = city;
        this.street = street;
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public String getFirstName() {return this.firstName;}
    public String getLastName() {return this.lastName;}
    public String getPassword() {return this.password;}
    public String getEmail() {return this.email;}
    public String getCity() {return this.city;}
    public String getStreet() {return this.street;}
    public int getId() {return this.id;}


}