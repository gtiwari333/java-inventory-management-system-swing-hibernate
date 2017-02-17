package com.ca.db.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ApplicationLog")
public class ApplicationLog {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "datetime")
    private Date dateTime;

    @Column(name = "dflag")
    private int dFlag;

    @Column(name = "message")
    private String message;

    @Column(name = "lastmodifieddate")
    private Date lastModifiedDate;

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

    public Date getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.dateTime == null ? 0 : this.dateTime.hashCode());
        result = 31 * result + this.id;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ApplicationLog other = (ApplicationLog) obj;
        if (this.dateTime == null) {
            if (other.dateTime != null)
                return false;
        } else if (!this.dateTime.equals(other.dateTime)) {
            return false;
        }
        return this.id == other.id;
    }
}