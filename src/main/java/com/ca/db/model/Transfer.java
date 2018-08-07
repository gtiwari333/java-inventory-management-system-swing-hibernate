package com.ca.db.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Transfer")
public class Transfer {
    public static final int OFFICIAL = 2;
    public static final int PERSONNAL = 1;
    public static final int LILAM = 3;
    public static final int STATUS_RETURNED_ALL = 0;
    public static final int STATUS_NOT_RETURNED = 1;
    public static final int HASTANTARAN_NOT_RECEIVED = 1;
    public static final int HASTANTARAN_RECEIVED = 2;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private BranchOffice branchOffice;

    @Column(name = "transferdate")
    private Date transferDate;

    @Column(name = "dflag")
    private int dFlag;

    @Column(name = "lastmodifieddate")
    private Date lastModifiedDate;

    private int transferType;

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private Person person;

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

    private String transferPanaNumber;

    @Column(name = "hastantaranReceivedStatus")
    private int hastantaranReceivedStatus;

    public int getHastantaranReceivedStatus() {
        return this.hastantaranReceivedStatus;
    }

    public void setHastantaranReceivedStatus(int hastantaranReceivedStatus) {
        this.hastantaranReceivedStatus = hastantaranReceivedStatus;
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

    public int getTransferType() {
        return this.transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
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

    public String getTransferPanaNumber() {
        return this.transferPanaNumber;
    }

    public void setTransferPanaNumber(String transferPanaNumber) {
        this.transferPanaNumber = transferPanaNumber;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
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
        StringBuilder builder = new StringBuilder();
        builder.append("\nTransfer [id=");
        builder.append(this.id);
        builder.append(", branchOffice=");
        builder.append(this.branchOffice);
        builder.append(", transferDate=");
        builder.append(this.transferDate);
        builder.append(", dFlag=");
        builder.append(this.dFlag);
        builder.append(", lastModifiedDate=");
        builder.append(this.lastModifiedDate);
        builder.append(", transferType=");
        builder.append(this.transferType);
        builder.append(", person=");
        builder.append(this.person);
        builder.append(", quantity=");
        builder.append(this.quantity);
        builder.append(", remainingQtyToReturn=");
        builder.append(this.remainingQtyToReturn);
        builder.append(", status=");
        builder.append(this.status);
        builder.append(", rate=");
        builder.append(this.rate);
        builder.append(", deliveredDate=");
        builder.append(this.deliveredDate);

        builder.append(", transferRequestNumber=");
        builder.append(this.transferRequestNumber);
        builder.append(", transferPanaNumber=");
        builder.append(this.transferPanaNumber);
        builder.append("]");
        return builder.toString();
    }
}