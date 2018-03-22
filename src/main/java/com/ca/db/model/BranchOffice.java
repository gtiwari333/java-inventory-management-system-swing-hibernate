package com.ca.db.model;

import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BranchOffice")
public class BranchOffice {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @Index(name = "name")
    private String name;

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

    public final String getName() {
        return this.name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getAddress() {
        return this.address;
    }

    public final void setAddress(String address) {
        this.address = address;
    }

    public final String getPhoneNumber() {
        return this.phoneNumber;
    }

    public final void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public final String toString() {
        return "BranchOffice [id=" + this.id + ", name=" + this.name + ", address=" + this.address + ", phoneNumber=" + this.phoneNumber + ", deleteStatus=" + this.dFlag + "]";
    }

    public final String getDistrict() {
        return this.district;
    }

    public final void setDistrict(String district) {
        this.district = district;
    }
}