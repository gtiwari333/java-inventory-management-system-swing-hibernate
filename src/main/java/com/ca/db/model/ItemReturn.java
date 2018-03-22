package com.ca.db.model;

import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ItemReturn")
public class ItemReturn {

    public static final int RETURN_ITEM_CONDITION_GOOD = 1;
    public static final int RETURN_ITEM_UNREPAIRABLE = 2;
    public static final int RETURN_NEEDS_REPAIR = 3;
    public static final int RETURN_EXEMPTION = 4;
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "returnitemcondition")
    private int returnItemCondition;

    @Column(name = "lastmodifieddate")
    private Date lastModifiedDate;

    @Column(name = "dflag")
    private int dFlag;

    @Column(name = "returnnumber")
    private String returnNumber;

    @Index(name = "transfer")
    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private Transfer transfer;

    @Column(name = "quantity")
    private int quantity;

    @Index(name = "status")
    @Column(name = "status")
    private int status;

    @Column(name = "addeddate")
    private Date addedDate;

    public final int getReturnItemCondition() {
        return this.returnItemCondition;
    }

    public final void setReturnItemCondition(int returnItemCondition) {
        this.returnItemCondition = returnItemCondition;
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int id) {
        this.id = id;
    }

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

    public final int getQuantity() {
        return this.quantity;
    }

    public final void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public final int getStatus() {
        return this.status;
    }

    public final void setStatus(int status) {
        this.status = status;
    }

    public final Date getAddedDate() {
        return this.addedDate;
    }

    public final void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public final String getReturnNumber() {
        return this.returnNumber;
    }

    public final void setReturnNumber(String returnNumber) {
        this.returnNumber = returnNumber;
    }

    public final Transfer getTransfer() {
        return this.transfer;
    }

    public final void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }
}