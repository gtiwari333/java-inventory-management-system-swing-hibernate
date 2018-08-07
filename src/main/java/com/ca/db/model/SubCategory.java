package com.ca.db.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SubCategory")
public class SubCategory {
    public static final int TYPE_NON_RETURNABLE = 2;
    public static final int TYPE_RETURNABLE = 1;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    private int categoryId;

    @Column(name = "subcategoryname")
    private String subCategoryName;

    @Column(name = "subcategorytype")
    private int subCategoryType;

    @Column(name = "lastmodifieddate")
    private Date lastModifiedDate;

    @Column(name = "dflag")
    private int dFlag;

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private CategorySpecifications categorySpecifications;

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public String getSubCategoryName() {
        return this.subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public int getSubCategoryType() {
        return this.subCategoryType;
    }

    public void setSubCategoryType(int subCategoryType) {
        this.subCategoryType = subCategoryType;
    }

    public CategorySpecifications getCategorySpecifications() {
        return this.categorySpecifications;
    }

    public void setCategorySpecifications(CategorySpecifications categorySpecifications) {
        this.categorySpecifications = categorySpecifications;
    }
}