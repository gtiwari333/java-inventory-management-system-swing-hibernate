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

    @OneToMany(cascade = {javax.persistence.CascadeType.ALL})
    @Column(name = "roles")
    private List<Role> roles;

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

    public final int getdFlag() {
        return this.dFlag;
    }

    public final void setdFlag(int dFlag) {
        this.dFlag = dFlag;
    }

    public final Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public final void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public final List<Role> getRoles() {
        return this.roles;
    }

    public final void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final String getUsername() {
        return this.username;
    }

    public final void setUsername(String username) {
        this.username = username;
    }

    public final String getPassword() {
        return this.password;
    }

    public final void setPassword(String password) {
        this.password = password;
    }

    public final int getInvalid_count() {
        return this.invalid_count;
    }

    public final void setInvalid_count(int invalid_count) {
        this.invalid_count = invalid_count;
    }

    public final Date getLastLoginDate() {
        return this.lastLoginDate;
    }

    public final void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public final String getZxtra1() {
        return this.zxtra1;
    }

    public final void setZxtra1(String zxtra1) {
        this.zxtra1 = zxtra1;
    }

    public final String getZxtra2() {
        return this.zxtra2;
    }

    public final void setZxtra2(String zxtra2) {
        this.zxtra2 = zxtra2;
    }

    public final String getZxtra3() {
        return this.zxtra3;
    }

    public final void setZxtra3(String zxtra3) {
        this.zxtra3 = zxtra3;
    }

    public final String getZxtra4() {
        return this.zxtra4;
    }

    public final void setZxtra4(String zxtra4) {
        this.zxtra4 = zxtra4;
    }

    public final String getZxtra5() {
        return this.zxtra5;
    }

    public final void setZxtra5(String zxtra5) {
        this.zxtra5 = zxtra5;
    }

    public final int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.id;
        result = 31 * result + (this.password == null ? 0 : this.password.hashCode());
        return result;
    }

    public final boolean equals(Object obj) {
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