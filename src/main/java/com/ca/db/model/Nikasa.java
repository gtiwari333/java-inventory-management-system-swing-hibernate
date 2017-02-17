package com.ca.db.model;

import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Nikasa")
public class Nikasa {
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

    @Column(name = "nikasadate")
    private Date nikasaDate;

    @Column(name = "dflag")
    private int dFlag;

    @Column(name = "lastmodifieddate")
    private Date lastModifiedDate;

    @Index(name = "nikasatype")
    private int nikasaType;

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

    @Index(name = "nikasarequestnumber")
    private String nikasaRequestNumber;

    @Index(name = "nikasapananumber")
    private String nikasaPanaNumber;

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

    public Date getNikasaDate() {
        return this.nikasaDate;
    }

    public void setNikasaDate(Date nikasaDate) {
        this.nikasaDate = nikasaDate;
    }

    public int getNikasaType() {
        return this.nikasaType;
    }

    public void setNikasaType(int nikasaType) {
        this.nikasaType = nikasaType;
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

    public String getNikasaPanaNumber() {
        return this.nikasaPanaNumber;
    }

    public void setNikasaPanaNumber(String nikasaPanaNumber) {
        this.nikasaPanaNumber = nikasaPanaNumber;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getNikasaRequestNumber() {
        return this.nikasaRequestNumber;
    }

    public void setNikasaRequestNumber(String nikasaRequestNumber) {
        this.nikasaRequestNumber = nikasaRequestNumber;
    }

    public int getRemainingQtyToReturn() {
        return this.remainingQtyToReturn;
    }

    public void setRemainingQtyToReturn(int remainingQtyToReturn) {
        this.remainingQtyToReturn = remainingQtyToReturn;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nNikasa [id=");
        builder.append(this.id);
        builder.append(", branchOffice=");
        builder.append(this.branchOffice);
        builder.append(", nikasaDate=");
        builder.append(this.nikasaDate);
        builder.append(", dFlag=");
        builder.append(this.dFlag);
        builder.append(", lastModifiedDate=");
        builder.append(this.lastModifiedDate);
        builder.append(", nikasaType=");
        builder.append(this.nikasaType);
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

        builder.append(", nikasaRequestNumber=");
        builder.append(this.nikasaRequestNumber);
        builder.append(", nikasaPanaNumber=");
        builder.append(this.nikasaPanaNumber);
        builder.append("]");
        return builder.toString();
    }
}