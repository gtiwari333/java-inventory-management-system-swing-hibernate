package com.ca.db.model;

import org.hibernate.annotations.Index;

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

    @Index(name = "branchoffice")
    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private BranchOffice branchOffice;

    @Column(name = "transferdate")
    private Date transferDate;

    @Column(name = "dflag")
    private int dFlag;

    @Column(name = "lastmodifieddate")
    private Date lastModifiedDate;

    @Index(name = "transfertype")
    private int transferType;

    @Index(name = "person")
    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private Person person;

    @Index(name = "item")
    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private Item item;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "remainingqtytoreturn")
    private int remainingQtyToReturn;

    @Index(name = "status")
    private int status;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "delivereddate")
    private Date deliveredDate;

    @Index(name = "transferrequestnumber")
    private String transferRequestNumber;

    @Index(name = "transferpananumber")
    private String transferPanaNumber;

    @Column(name = "hastantaranReceivedStatus")
    private int hastantaranReceivedStatus;

    public final int getHastantaranReceivedStatus() {
        return this.hastantaranReceivedStatus;
    }

    public final void setHastantaranReceivedStatus(int hastantaranReceivedStatus) {
        this.hastantaranReceivedStatus = hastantaranReceivedStatus;
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

    public final int getId() {
        return this.id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final BranchOffice getBranchOffice() {
        return this.branchOffice;
    }

    public final void setBranchOffice(BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }

    public final Date getTransferDate() {
        return this.transferDate;
    }

    public final void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public final int getTransferType() {
        return this.transferType;
    }

    public final void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public final Item getItem() {
        return this.item;
    }

    public final void setItem(Item item) {
        this.item = item;
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

    public final BigDecimal getRate() {
        return this.rate;
    }

    public final void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public final Date getDeliveredDate() {
        return this.deliveredDate;
    }

    public final void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public final String getTransferPanaNumber() {
        return this.transferPanaNumber;
    }

    public final void setTransferPanaNumber(String transferPanaNumber) {
        this.transferPanaNumber = transferPanaNumber;
    }

    public final Person getPerson() {
        return this.person;
    }

    public final void setPerson(Person person) {
        this.person = person;
    }

    public final String getTransferRequestNumber() {
        return this.transferRequestNumber;
    }

    public final void setTransferRequestNumber(String transferRequestNumber) {
        this.transferRequestNumber = transferRequestNumber;
    }

    public final int getRemainingQtyToReturn() {
        return this.remainingQtyToReturn;
    }

    public final void setRemainingQtyToReturn(int remainingQtyToReturn) {
        this.remainingQtyToReturn = remainingQtyToReturn;
    }

    public final String toString() {
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