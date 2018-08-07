package com.ca.db.model;

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

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private Transfer transfer;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "status")
    private int status;

    @Column(name = "addeddate")
    private Date addedDate;

    public int getReturnItemCondition() {
        return this.returnItemCondition;
    }

    public void setReturnItemCondition(int returnItemCondition) {
        this.returnItemCondition = returnItemCondition;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getAddedDate() {
        return this.addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public String getReturnNumber() {
        return this.returnNumber;
    }

    public void setReturnNumber(String returnNumber) {
        this.returnNumber = returnNumber;
    }

    public Transfer getTransfer() {
        return this.transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }
}