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

    public final int getId() {
        return this.id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final Date getDateTime() {
        return this.dateTime;
    }

    public final void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public final int getdFlag() {
        return this.dFlag;
    }

    public final void setdFlag(int dFlag) {
        this.dFlag = dFlag;
    }

    public final String getValue() {
        return this.value;
    }

    public final void setValue(String value) {
        this.value = value;
    }

    public final Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public final void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}