package com.ca.db.model;

import org.hibernate.annotations.Index;

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

    @Index(name = "branchoffice")
    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private BranchOffice branchOffice;

    @Column(name = "requestdate")
    private Date requestDate;

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

    @Index(name = "specification")
    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private Specification specification;

    @Column(name = "quantity")
    private int quantity;

    @Index(name = "status")
    private int status;

    @Index(name = "requestpananumber")
    private String requestPanaNumber;

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

    public final Person getPerson() {
        return this.person;
    }

    public final void setPerson(Person person) {
        this.person = person;
    }

    public final Date getRequestDate() {
        return this.requestDate;
    }

    public final void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public final Specification getSpecification() {
        return this.specification;
    }

    public final void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public final String getRequestPanaNumber() {
        return this.requestPanaNumber;
    }

    public final void setRequestPanaNumber(String requestPanaNumber) {
        this.requestPanaNumber = requestPanaNumber;
    }
}