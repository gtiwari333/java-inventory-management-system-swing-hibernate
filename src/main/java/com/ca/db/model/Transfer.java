package com.ca.db.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Transfer")
public class Transfer {
    public static final int STATUS_RETURNED_ALL = 2;
    public static final int STATUS_NOT_RETURNED = 1;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private BranchOffice branchOffice; //lend to another office

    @Column(name = "transferdate")
    private Date transferDate;

    @Column(name = "dflag")
    private int dFlag;

    @Column(name = "lastmodifieddate")
    private Date lastModifiedDate;

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private Item item;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "remainingqtytoreturn")
    private int remainingQtyToReturn;

    private int status;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "delivereddate")
    private Date deliveredDate;

    private String transferRequestNumber;

    @Column(name = "receiveStatus")
    private int receiveStatus;

    public int getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(int receiveStatus) {
        this.receiveStatus = receiveStatus;
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

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BranchOffice getBranchOffice() {
        return this.branchOffice;
    }

    public void setBranchOffice(BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }

    public Date getTransferDate() {
        return this.transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
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

    public BigDecimal getRate() {
        return this.rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Date getDeliveredDate() {
        return this.deliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getTransferRequestNumber() {
        return this.transferRequestNumber;
    }

    public void setTransferRequestNumber(String transferRequestNumber) {
        this.transferRequestNumber = transferRequestNumber;
    }

    public int getRemainingQtyToReturn() {
        return this.remainingQtyToReturn;
    }

    public void setRemainingQtyToReturn(int remainingQtyToReturn) {
        this.remainingQtyToReturn = remainingQtyToReturn;
    }

    public String toString() {

        String builder = "\nTransfer [id=" +
                this.id +
                ", branchOffice=" +
                this.branchOffice +
                ", transferDate=" +
                this.transferDate +
                ", dFlag=" +
                this.dFlag +
                ", lastModifiedDate=" +
                this.lastModifiedDate +
                ", quantity=" +
                this.quantity +
                ", remainingQtyToReturn=" +
                this.remainingQtyToReturn +
                ", status=" +
                this.status +
                ", rate=" +
                this.rate +
                ", deliveredDate=" +
                this.deliveredDate +
                ", transferRequestNumber=" +
                this.transferRequestNumber +
                "]";
        return builder;
    }
}