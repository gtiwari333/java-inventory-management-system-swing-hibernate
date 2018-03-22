package com.ca.db.model;

import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Specification")
public class Specification {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "specification1")
    @Index(name = "specification1")
    private String specification1;

    @Column(name = "specification2")
    @Index(name = "specification2")
    private String specification2;

    @Column(name = "specification3")
    @Index(name = "specification3")
    private String specification3;

    @Column(name = "specification4")
    @Index(name = "specification4")
    private String specification4;

    @Column(name = "specification5")
    @Index(name = "specification5")
    private String specification5;

    @Column(name = "specification6")
    private String specification6;

    @Column(name = "specification7")
    private String specification7;

    @Column(name = "specification8")
    private String specification8;

    @Column(name = "specification9")
    private String specification9;

    @Column(name = "specification10")
    private String specification10;

    @Column(name = "dflag")
    private int dFlag;

    @Column(name = "lastmodifieddate")
    private Date lastModifiedDate;

    @Column(name = "activestatus")
    private int activeStatus;

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

    public final int getActiveStatus() {
        return this.activeStatus;
    }

    public final void setActiveStatus(int activeStatus) {
        this.activeStatus = activeStatus;
    }

    public final String getSpecification1() {
        return this.specification1;
    }

    public final void setSpecification1(String specification1) {
        this.specification1 = specification1;
    }

    public final String getSpecification2() {
        return this.specification2;
    }

    public final void setSpecification2(String specification2) {
        this.specification2 = specification2;
    }

    public final String getSpecification3() {
        return this.specification3;
    }

    public final void setSpecification3(String specification3) {
        this.specification3 = specification3;
    }

    public final String getSpecification4() {
        return this.specification4;
    }

    public final void setSpecification4(String specification4) {
        this.specification4 = specification4;
    }

    public final String getSpecification5() {
        return this.specification5;
    }

    public final void setSpecification5(String specification5) {
        this.specification5 = specification5;
    }

    public final String getSpecification6() {
        return this.specification6;
    }

    public final void setSpecification6(String specification6) {
        this.specification6 = specification6;
    }

    public final String getSpecification7() {
        return this.specification7;
    }

    public final void setSpecification7(String specification7) {
        this.specification7 = specification7;
    }

    public final String getSpecification8() {
        return this.specification8;
    }

    public final void setSpecification8(String specification8) {
        this.specification8 = specification8;
    }

    public final String getSpecification9() {
        return this.specification9;
    }

    public final void setSpecification9(String specification9) {
        this.specification9 = specification9;
    }

    public final String getSpecification10() {
        return this.specification10;
    }

    public final void setSpecification10(String specification10) {
        this.specification10 = specification10;
    }
}