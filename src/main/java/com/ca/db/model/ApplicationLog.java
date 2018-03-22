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

    public final String getMessage() {
        return this.message;
    }

    public final void setMessage(String message) {
        this.message = message;
    }

    public final int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.dateTime == null ? 0 : this.dateTime.hashCode());
        result = 31 * result + this.id;
        return result;
    }

    public final boolean equals(Object obj) {
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