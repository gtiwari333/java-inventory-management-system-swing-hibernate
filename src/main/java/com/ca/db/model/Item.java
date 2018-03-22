package com.ca.db.model;

import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Item")
public class Item {
    public static final Integer ADD_TYPE_NEW_ENTRY = 1;
    public static final Integer ADD_TYPE_RETURNED_ENTRY = 2;

    public static final Integer ACCOUNT_TRANSFERRED_TO_NEW = 22;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @Index(name = "name")
    private String name;

    @Column(name = "rackno")
    @Index(name = "rackno")
    private String rackNo;

    @Column(name = "rate")
    private BigDecimal rate;

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    @Index(name = "unitsstring")
    private UnitsString unitsString;

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

    @Column(name = "originalquantity")
    private int originalQuantity;

    @Index(name = "status")
    private int status;

    @Column(name = "serialnumber")
    private String serialNumber;

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    @Index(name = "vendor")
    private Vendor vendor;

    @Column(name = "purchasedate")
    private Date purchaseDate;

    @Column(name = "addeddate")
    private Date addedDate;

    @Column(name = "dflag")
    private int dFlag;

    @Column(name = "lastmodifieddate")
    private Date lastModifiedDate;

    @Column(name = "purchaseorderno")
    private String purchaseOrderNo;

    @Index(name = "pananumber")
    private String panaNumber;

    @Column(name = "addedtype")
    private int addedType;

    @Column(name = "parentitemid")
    private int parentItemId;

    @Column(name = "accounttransferstatus")
    private int accountTransferStatus;

    @Column(name = "currentfiscalyear")
    private int currentFiscalYear;

    @Column(name = "dakhilano")
    private String dakhilaNumber;

    @Column(name = "khatanumber")
    private String khataNumber;

    @Column(name = "zxtra1")
    private String zxtra1;

    @Column(name = "zxtra2")
    private String zxtra2;

    @Column(name = "zxtra3")
    private String zxtra3;

    @Column(name = "zxtra4")
    private String zxtra4;

    @Column(name = "zxtra5")
    private String zxtra5;

    private static boolean isEmpty(String str) {
        return str == null || str.trim().length() < 1;
    }

    public final int getAccountTransferStatus() {
        return this.accountTransferStatus;
    }

    public final void setAccountTransferStatus(int accountTransferStatus) {
        this.accountTransferStatus = accountTransferStatus;
    }

    public final String getPurchaseOrderNo() {
        return this.purchaseOrderNo;
    }

    public final void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public final String getPanaNumber() {
        return this.panaNumber;
    }

    public final void setPanaNumber(String panaNumber) {
        this.panaNumber = panaNumber;
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

    public final String getPartsNumber() {
        return this.partsNumber;
    }

    public final void setPartsNumber(String partsNumber) {
        this.partsNumber = partsNumber;
    }

    public final Date getPurchaseDate() {
        return this.purchaseDate;
    }

    public final void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public final Date getAddedDate() {
        return this.addedDate;
    }

    public final void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public final Vendor getVendor() {
        return this.vendor;
    }

    public final void setVendor(Vendor vendor) {
        this.vendor = vendor;
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

    public final int getQuantity() {
        return this.quantity;
    }

    public final void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final String getRackNo() {
        return this.rackNo;
    }

    public final void setRackNo(String rackNo) {
        this.rackNo = rackNo;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final BigDecimal getRate() {
        return this.rate;
    }

    public final void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public final int getStatus() {
        return this.status;
    }

    public final void setStatus(int status) {
        this.status = status;
    }

    public final String getSerialNumber() {
        return this.serialNumber;
    }

    public final void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public final String getZxtra1() {
        return this.zxtra1;
    }

    public final void setZxtra1(String zxtra1) {
        this.zxtra1 = zxtra1;
    }

    public final String getZxtra2() {
        return this.zxtra2;
    }

    public final void setZxtra2(String zxtra2) {
        this.zxtra2 = zxtra2;
    }

    public final String getZxtra3() {
        return this.zxtra3;
    }

    public final void setZxtra3(String zxtra3) {
        this.zxtra3 = zxtra3;
    }

    public final String getZxtra4() {
        return this.zxtra4;
    }

    public final void setZxtra4(String zxtra4) {
        this.zxtra4 = zxtra4;
    }

    public final String getZxtra5() {
        return this.zxtra5;
    }

    public final void setZxtra5(String zxtra5) {
        this.zxtra5 = zxtra5;
    }

    public final int getOriginalQuantity() {
        return this.originalQuantity;
    }

    public final void setOriginalQuantity(int originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    public final int getAddedType() {
        return this.addedType;
    }

    public final void setAddedType(int addedType) {
        this.addedType = addedType;
    }

    public final int getParentItemId() {
        return this.parentItemId;
    }

    public final void setParentItemId(int parentItemId) {
        this.parentItemId = parentItemId;
    }

    public final int getCurrentFiscalYear() {
        return this.currentFiscalYear;
    }

    public final void setCurrentFiscalYear(int currentFiscalYear) {
        this.currentFiscalYear = currentFiscalYear;
    }

    public final String getDakhilaNumber() {
        return this.dakhilaNumber;
    }

    public final void setDakhilaNumber(String dakhilaNumber) {
        this.dakhilaNumber = dakhilaNumber;
    }

    public final String getKhataNumber() {
        return this.khataNumber;
    }

    public final void setKhataNumber(String khataNumber) {
        this.khataNumber = khataNumber;
    }

    public final UnitsString getUnitsString() {
        return this.unitsString;
    }

    public final void setUnitsString(UnitsString unitsString) {
        this.unitsString = unitsString;
    }

    public final String getSpeciifcationString() {
        StringBuilder sb = new StringBuilder();
        Category c = getCategory();
        Specification sp = getSpecification();
        if ((!isEmpty(c.getSpecification1())) && (!isEmpty(sp.getSpecification1()))) {
            sb.append(c.getSpecification1()).append(" : ").append(sp.getSpecification1());
        }
        if ((!isEmpty(c.getSpecification2())) && (!isEmpty(sp.getSpecification2()))) {
            sb.append(" , ").append(c.getSpecification2()).append(" : ").append(sp.getSpecification2());
        }
        if ((!isEmpty(c.getSpecification3())) && (!isEmpty(sp.getSpecification3()))) {
            sb.append(" , ").append(c.getSpecification3()).append(" : ").append(sp.getSpecification3());
        }
        if ((!isEmpty(c.getSpecification4())) && (!isEmpty(sp.getSpecification4()))) {
            sb.append(" , ").append(c.getSpecification4()).append(" : ").append(sp.getSpecification4());
        }
        if ((!isEmpty(c.getSpecification5())) && (!isEmpty(sp.getSpecification5()))) {
            sb.append(" , ").append(c.getSpecification5()).append(" : ").append(sp.getSpecification5());
        }
        if ((!isEmpty(c.getSpecification6())) && (!isEmpty(sp.getSpecification6()))) {
            sb.append(" , ").append(c.getSpecification6()).append(" : ").append(sp.getSpecification6());
        }
        if ((!isEmpty(c.getSpecification7())) && (!isEmpty(sp.getSpecification7()))) {
            sb.append(" , ").append(c.getSpecification7()).append(" : ").append(sp.getSpecification7());
        }
        if ((!isEmpty(c.getSpecification8())) && (!isEmpty(sp.getSpecification8()))) {
            sb.append(" , ").append(c.getSpecification8()).append(" : ").append(sp.getSpecification8());
        }
        if ((!isEmpty(c.getSpecification9())) && (!isEmpty(sp.getSpecification9()))) {
            sb.append(" , ").append(c.getSpecification9()).append(" : ").append(sp.getSpecification9());
        }
        if ((!isEmpty(c.getSpecification10())) && (!isEmpty(sp.getSpecification10()))) {
            sb.append(" , ").append(c.getSpecification10()).append(" : ").append(sp.getSpecification10());
        }

        return sb.toString();
    }

    public final String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nItem [id=");
        builder.append(this.id);
        builder.append(", name=");
        builder.append(this.name);
        builder.append(", rackNo=");
        builder.append(this.rackNo);
        builder.append(", rate=");
        builder.append(this.rate);
        builder.append(", unitsString=");
        builder.append(this.unitsString);
        builder.append(", partsNumber=");
        builder.append(this.partsNumber);
        builder.append(", quantity=");
        builder.append(this.quantity);
        builder.append(", originalQuantity=");
        builder.append(this.originalQuantity);
        builder.append(", status=");
        builder.append(this.status);
        builder.append(", serialNumber=");
        builder.append(this.serialNumber);
        builder.append(", purchaseDate=");
        builder.append(this.purchaseDate);
        builder.append(", addedDate=");
        builder.append(this.addedDate);
        builder.append(", dFlag=");
        builder.append(this.dFlag);
        builder.append(", lastModifiedDate=");
        builder.append(this.lastModifiedDate);
        builder.append(", purchaseOrderNo=");
        builder.append(this.purchaseOrderNo);
        builder.append(", panaNumber=");
        builder.append(this.panaNumber);
        builder.append(", addedType=");
        builder.append(this.addedType);
        builder.append(", parentItemId=");
        builder.append(this.parentItemId);
        builder.append(", accountTransferStatus=");
        builder.append(this.accountTransferStatus);
        builder.append(", currentFiscalYear=");
        builder.append(this.currentFiscalYear);
        builder.append(", dakhilaNumber=");
        builder.append(this.dakhilaNumber);
        builder.append(", khataNumber=");
        builder.append(this.khataNumber);
        builder.append("]");
        return builder.toString();
    }
}