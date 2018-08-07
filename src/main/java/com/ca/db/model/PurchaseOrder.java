package com.ca.db.model;

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

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private Specification specification;

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private Category category;

    @Column(name = "partsnumber")
    private String partsNumber;

    @Column(name = "quantity")
    private int quantity;

    private int status;

    @Column(name = "orderdate")
    private Date orderDate;

    @Column(name = "addeddate")
    private Date addedDate;

    public int getdFlag() {
        return this.dFlag;
    }

    public void setdFlag(int dFlag) {
        this.dFlag = dFlag;
    }

    public Specification getSpecification() {
        return this.specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPartsNumber() {
        return this.partsNumber;
    }

    public void setPartsNumber(String partsNumber) {
        this.partsNumber = partsNumber;
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

    public Date getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getAddedDate() {
        return this.addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
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
}