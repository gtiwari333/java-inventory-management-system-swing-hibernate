package com.ca.db.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "UnitsString")
public class UnitsString {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "datetime")
    private Date dateTime;

    @Column(name = "dflag")
    private int dFlag;

    @Column(name = "value")
    private String value;

    @Column(name = "lastmodifieddate")
    private Date lastModifiedDate;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getdFlag() {
        return this.dFlag;
    }

    public void setdFlag(int dFlag) {
        this.dFlag = dFlag;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}