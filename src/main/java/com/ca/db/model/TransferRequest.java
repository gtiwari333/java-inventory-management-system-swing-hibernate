package com.ca.db.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TransferRequest")
public class TransferRequest {
    public static final int OFFICIAL = 2;
    public static final int PESONNAL = 1;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private BranchOffice branchOffice;

    @Column(name = "requestdate")
    private Date requestDate;

    @Column(name = "dflag")
    private int dFlag;

    @Column(name = "lastmodifieddate")
    private Date lastModifiedDate;

    private int transferType;

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private Person person;

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private Item item;

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private Specification specification;

    @Column(name = "quantity")
    private int quantity;

    private int status;

    private String requestPanaNumber;

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

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getRequestDate() {
        return this.requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Specification getSpecification() {
        return this.specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public String getRequestPanaNumber() {
        return this.requestPanaNumber;
    }

    public void setRequestPanaNumber(String requestPanaNumber) {
        this.requestPanaNumber = requestPanaNumber;
    }
}