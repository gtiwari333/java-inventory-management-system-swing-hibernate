package com.ca.db.model;

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
    private String name;

    @Column(name = "rackno")
    private String rackNo; //warehouse rack number

    @Column(name = "rate")
    private BigDecimal rate;

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private UnitsString unitsString;

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private Specification specification;

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private Category category;

    @Column(name = "partsnumber")
    private String partsNumber;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "originalquantity")
    private int originalQuantity;

    private int status;

    @Column(name = "serialnumber")
    private String serialNumber;

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
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

    @Column(name = "addedtype")
    private int addedType;

    @Column(name = "accounttransferstatus")
    private int accountTransferStatus;

    @Column(name = "currentfiscalyear")
    private int currentFiscalYear;

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

    private static boolean isNotEmpty(String str) {
        return str != null && str.trim().length() >= 1;
    }

    public int getAccountTransferStatus() {
        return this.accountTransferStatus;
    }

    public void setAccountTransferStatus(int accountTransferStatus) {
        this.accountTransferStatus = accountTransferStatus;
    }

    public String getPurchaseOrderNo() {
        return this.purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
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

    public String getPartsNumber() {
        return this.partsNumber;
    }

    public void setPartsNumber(String partsNumber) {
        this.partsNumber = partsNumber;
    }

    public Date getPurchaseDate() {
        return this.purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getAddedDate() {
        return this.addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Vendor getVendor() {
        return this.vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
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

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRackNo() {
        return this.rackNo;
    }

    public void setRackNo(String rackNo) {
        this.rackNo = rackNo;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRate() {
        return this.rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getZxtra1() {
        return this.zxtra1;
    }

    public void setZxtra1(String zxtra1) {
        this.zxtra1 = zxtra1;
    }

    public String getZxtra2() {
        return this.zxtra2;
    }

    public void setZxtra2(String zxtra2) {
        this.zxtra2 = zxtra2;
    }

    public String getZxtra3() {
        return this.zxtra3;
    }

    public void setZxtra3(String zxtra3) {
        this.zxtra3 = zxtra3;
    }

    public String getZxtra4() {
        return this.zxtra4;
    }

    public void setZxtra4(String zxtra4) {
        this.zxtra4 = zxtra4;
    }

    public String getZxtra5() {
        return this.zxtra5;
    }

    public void setZxtra5(String zxtra5) {
        this.zxtra5 = zxtra5;
    }

    public int getOriginalQuantity() {
        return this.originalQuantity;
    }

    public void setOriginalQuantity(int originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    public int getAddedType() {
        return this.addedType;
    }

    public void setAddedType(int addedType) {
        this.addedType = addedType;
    }

    public int getCurrentFiscalYear() {
        return this.currentFiscalYear;
    }

    public void setCurrentFiscalYear(int currentFiscalYear) {
        this.currentFiscalYear = currentFiscalYear;
    }

    public UnitsString getUnitsString() {
        return this.unitsString;
    }

    public void setUnitsString(UnitsString unitsString) {
        this.unitsString = unitsString;
    }

    public String getSpeciifcationString() {
        StringBuilder sb = new StringBuilder();
        Category c = getCategory();
        Specification sp = getSpecification();
        if (sp == null) {
            return "NO SPECS";
        }
        if ((isNotEmpty(c.getSpecification1())) && (isNotEmpty(sp.getSpecification1()))) {
            sb.append(c.getSpecification1()).append(" : ").append(sp.getSpecification1());
        }
        if ((isNotEmpty(c.getSpecification2())) && (isNotEmpty(sp.getSpecification2()))) {
            sb.append(" , ").append(c.getSpecification2()).append(" : ").append(sp.getSpecification2());
        }
        if ((isNotEmpty(c.getSpecification3())) && (isNotEmpty(sp.getSpecification3()))) {
            sb.append(" , ").append(c.getSpecification3()).append(" : ").append(sp.getSpecification3());
        }
        if ((isNotEmpty(c.getSpecification4())) && (isNotEmpty(sp.getSpecification4()))) {
            sb.append(" , ").append(c.getSpecification4()).append(" : ").append(sp.getSpecification4());
        }
        if ((isNotEmpty(c.getSpecification5())) && (isNotEmpty(sp.getSpecification5()))) {
            sb.append(" , ").append(c.getSpecification5()).append(" : ").append(sp.getSpecification5());
        }
        if ((isNotEmpty(c.getSpecification6())) && (isNotEmpty(sp.getSpecification6()))) {
            sb.append(" , ").append(c.getSpecification6()).append(" : ").append(sp.getSpecification6());
        }
        if ((isNotEmpty(c.getSpecification7())) && (isNotEmpty(sp.getSpecification7()))) {
            sb.append(" , ").append(c.getSpecification7()).append(" : ").append(sp.getSpecification7());
        }
        if ((isNotEmpty(c.getSpecification8())) && (isNotEmpty(sp.getSpecification8()))) {
            sb.append(" , ").append(c.getSpecification8()).append(" : ").append(sp.getSpecification8());
        }
        if ((isNotEmpty(c.getSpecification9())) && (isNotEmpty(sp.getSpecification9()))) {
            sb.append(" , ").append(c.getSpecification9()).append(" : ").append(sp.getSpecification9());
        }
        if ((isNotEmpty(c.getSpecification10())) && (isNotEmpty(sp.getSpecification10()))) {
            sb.append(" , ").append(c.getSpecification10()).append(" : ").append(sp.getSpecification10());
        }

        return sb.toString();
    }

    public String toString() {
        String builder = "\nItem [id=" +
                this.id +
                ", name=" +
                this.name +
                ", rackNo=" +
                this.rackNo +
                ", rate=" +
                this.rate +
                ", unitsString=" +
                this.unitsString +
                ", partsNumber=" +
                this.partsNumber +
                ", quantity=" +
                this.quantity +
                ", originalQuantity=" +
                this.originalQuantity +
                ", status=" +
                this.status +
                ", serialNumber=" +
                this.serialNumber +
                ", purchaseDate=" +
                this.purchaseDate +
                ", addedDate=" +
                this.addedDate +
                ", dFlag=" +
                this.dFlag +
                ", lastModifiedDate=" +
                this.lastModifiedDate +
                ", purchaseOrderNo=" +
                this.purchaseOrderNo +
                ", addedType=" +
                this.addedType +
                ", accountTransferStatus=" +
                this.accountTransferStatus +
                ", currentFiscalYear=" +
                this.currentFiscalYear +
                "]";
        return builder;
    }
}