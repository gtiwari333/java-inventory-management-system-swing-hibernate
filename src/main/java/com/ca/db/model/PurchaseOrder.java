package com.ca.db.model;

import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PurchaseOrder")
public class PurchaseOrder {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "lastmodifieddate")
    private Date lastModifiedDate;

    @Column(name = "dflag")
    private int dFlag;

    @Index(name = "specification")
    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private Specification specification;

    @Index(name = "category")
    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private Category category;

    @Column(name = "partsnumber")
    private String partsNumber;

    @Column(name = "quantity")
    private int quantity;

    @Index(name = "status")
    private int status;

    @Column(name = "orderdate")
    private Date orderDate;

    @Column(name = "addeddate")
    private Date addedDate;

    public final int getdFlag() {
        return this.dFlag;
    }

    public final void setdFlag(int dFlag) {
        this.dFlag = dFlag;
    }

    public final Specification getSpecification() {
        return this.specification;
    }

    public final void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public final Category getCategory() {
        return this.category;
    }

    public final void setCategory(Category category) {
        this.category = category;
    }

    public final String getPartsNumber() {
        return this.partsNumber;
    }

    public final void setPartsNumber(String partsNumber) {
        this.partsNumber = partsNumber;
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

    public final Date getOrderDate() {
        return this.orderDate;
    }

    public final void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public final Date getAddedDate() {
        return this.addedDate;
    }

    public final void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
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
}