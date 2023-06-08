package com.example.makeameal.Domain;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class Cook implements Serializable {

    @SerializedName("roles")
    @Expose
    private List<String> roles = null;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("emailAdress")
    @Expose
    private String emailAdress;


    /**
     *
     * @param firstName
     * @param lastName
     * @param phoneNumber
     * @param city
     * @param street
     * @param roles
     * @param emailAdress
     * @param id
     * @param isActive
     */


    public Cook(List<String> roles, Boolean isActive, String phoneNumber, Integer id, String firstName, String lastName, String street, String city, String emailAdress) {
        super();
        this.roles = roles;
        this.isActive = isActive;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.emailAdress = emailAdress;
    }

    public List<String> getRoles() {
        return roles;
    }


    public Boolean getIsActive() {
        return isActive;
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }



    public String getLastName() {
        return lastName;
    }



    public String getStreet() {
        return street;
    }


    public String getCity() {
        return city;
    }



    public String getEmailAdress() {
        return emailAdress;
    }



}