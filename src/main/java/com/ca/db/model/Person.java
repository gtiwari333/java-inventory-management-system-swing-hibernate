package com.ca.db.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "district")
    private String district;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "dflag")
    private int dFlag;

    @Column(name = "lastmodifieddate")
    private Date lastModifiedDate;

    public final Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public final void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public final int getdFlag() {
        return this.dFlag;
    }

    public final void setdFlag(int dFlag) {
        this.dFlag = dFlag;
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final String getFirstName() {
        return this.firstName;
    }

    public final void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public final String getLastName() {
        return this.lastName;
    }

    public final void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public final String getAddress() {
        return this.address;
    }

    public final void setAddress(String address) {
        this.address = address;
    }

    public final String getDistrict() {
        return this.district;
    }

    public final void setDistrict(String district) {
        this.district = district;
    }

    public final String getPhoneNumber() {
        return this.phoneNumber;
    }

    public final void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}