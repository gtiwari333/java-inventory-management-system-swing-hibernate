package com.ca.db.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "userid")
    private int userId;

    @Column(name = "role")
    private String role;

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

    public final int getUserId() {
        return this.userId;
    }

    public final void setUserId(int userId) {
        this.userId = userId;
    }

    public final String getRole() {
        return this.role;
    }

    public final void setRole(String role) {
        this.role = role;
    }
}