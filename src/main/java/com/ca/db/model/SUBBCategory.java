package com.ca.db.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Category")
public class SUBBCategory {
    public static final int TYPE_NON_RETURNABLE = 2;
    public static final int TYPE_RETURNABLE = 1;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "lastmodifieddate")
    private Date lastModifiedDate;

    @Column(name = "categoryname")
    private String categoryName;

    @OneToMany(cascade = {javax.persistence.CascadeType.ALL})
    @Column(name = "subCategory")
    private List<SubCategory> subCategory;

    @Column(name = "dflag")
    private int dFlag;

    public final void addSubCategory(SubCategory aSubCategory) {
        if (this.subCategory == null) {
            this.subCategory = new ArrayList();
            System.out.println("Category.addSubCategory() >> New - adding sub category");
        }
        this.subCategory.add(aSubCategory);
        aSubCategory.setCategoryId(this.id);
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

    public final String getCategoryName() {
        return this.categoryName;
    }

    public final void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public final List<SubCategory> getSubCategory() {
        return this.subCategory;
    }

    public final void setSubCategory(List<SubCategory> subCategory) {
        this.subCategory = subCategory;
    }

    public final int getdFlag() {
        return this.dFlag;
    }

    public final void setdFlag(int dFlag) {
        this.dFlag = dFlag;
    }
}