package com.ca.db.model;

import org.hibernate.annotations.Index;

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

    @Index(name = "categoryid")
    private int categoryId;

    @Column(name = "subcategoryname")
    @Index(name = "subcategoryname")
    private String subCategoryName;

    @Column(name = "subcategorytype")
    private int subCategoryType;

    @Column(name = "lastmodifieddate")
    private Date lastModifiedDate;

    @Column(name = "dflag")
    private int dFlag;

    @OneToOne(cascade = {javax.persistence.CascadeType.ALL})
    private CategorySpecifications categorySpecifications;

    public final int getCategoryId() {
        return this.categoryId;
    }

    public final void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public final String getSubCategoryName() {
        return this.subCategoryName;
    }

    public final void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public final int getSubCategoryType() {
        return this.subCategoryType;
    }

    public final void setSubCategoryType(int subCategoryType) {
        this.subCategoryType = subCategoryType;
    }

    public final CategorySpecifications getCategorySpecifications() {
        return this.categorySpecifications;
    }

    public final void setCategorySpecifications(CategorySpecifications categorySpecifications) {
        this.categorySpecifications = categorySpecifications;
    }
}