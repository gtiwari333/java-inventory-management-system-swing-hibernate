package com.ca.db.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @JoinColumn(name = "id")
    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private Department dept;

    @Column(name = "f_name")
    private String fname;

    @Column(name = "l_name")
    private String lname;

    @Column(name = "title")
    private String title;

    @Column(name = "dflag")
    private int dFlag;

    @Column(name = "lastmodifieddate")
    private Date lastModifiedDate;

    @Column(name = "extra1")
    private String extra1;

    @Column(name = "extra2")
    private String extra2;

    @Column(name = "extra3")
    private String extra3;

    @Column(name = "extra4")
    private String extra4;

    @Column(name = "extra5")
    private String extra5;

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getdFlag() {
        return this.dFlag;
    }

    public void setdFlag(int dFlag) {
        this.dFlag = dFlag;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Department getDept() {
        return this.dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public String getFname() {
        return this.fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return this.lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtra1() {
        return this.extra1;
    }

    public void setExtra1(String extra1) {
        this.extra1 = extra1;
    }

    public String getExtra2() {
        return this.extra2;
    }

    public void setExtra2(String extra2) {
        this.extra2 = extra2;
    }

    public String getExtra3() {
        return this.extra3;
    }

    public void setExtra3(String extra3) {
        this.extra3 = extra3;
    }

    public String getExtra4() {
        return this.extra4;
    }

    public void setExtra4(String extra4) {
        this.extra4 = extra4;
    }

    public String getExtra5() {
        return this.extra5;
    }

    public void setExtra5(String extra5) {
        this.extra5 = extra5;
    }
}