package com.ca.db.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "LoginUser")
public class LoginUser {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "dflag")
    private int dFlag;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "invalid_count")
    private int invalid_count;

    @Column(name = "last_login_date")
    private Date lastLoginDate;

    @Column(name = "lastmodifieddate")
    private Date lastModifiedDate;

    @Column(name = "zxtra1")
    private String zxtra1;

    @Column(name = "zxtra2")
    private String zxtra2;

    @Column(name = "zxtra3")
    private String zxtra3;

    @Column(name = "zxtra4")
    private String zxtra4;

    @Column(name = "zxtra5")
    private String zxtra5;

    public int getdFlag() {
        return this.dFlag;
    }

    public void setdFlag(int dFlag) {
        this.dFlag = dFlag;
    }

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getInvalid_count() {
        return this.invalid_count;
    }

    public void setInvalid_count(int invalid_count) {
        this.invalid_count = invalid_count;
    }

    public Date getLastLoginDate() {
        return this.lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getZxtra1() {
        return this.zxtra1;
    }

    public void setZxtra1(String zxtra1) {
        this.zxtra1 = zxtra1;
    }

    public String getZxtra2() {
        return this.zxtra2;
    }

    public void setZxtra2(String zxtra2) {
        this.zxtra2 = zxtra2;
    }

    public String getZxtra3() {
        return this.zxtra3;
    }

    public void setZxtra3(String zxtra3) {
        this.zxtra3 = zxtra3;
    }

    public String getZxtra4() {
        return this.zxtra4;
    }

    public void setZxtra4(String zxtra4) {
        this.zxtra4 = zxtra4;
    }

    public String getZxtra5() {
        return this.zxtra5;
    }

    public void setZxtra5(String zxtra5) {
        this.zxtra5 = zxtra5;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.id;
        result = 31 * result + (this.password == null ? 0 : this.password.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LoginUser other = (LoginUser) obj;
        if (this.id != other.id)
            return false;
        if (this.password == null) {
            if (other.password != null)
                return false;
        } else if (!this.password.equals(other.password))
            return false;
        return true;
    }
}