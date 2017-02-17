package com.ca.ui.report;

public class ReportBean {

    private int itemId;
    private String date;
    private String goodsName;
    private String entryFormId; // dakhila number
    private String khataPanaNumber;
    private String supplier;
    private String specification;
    private String inQty;
    private String inRate;
    private String inTotal;
    private String reqFormId;
    private String nikasaBranch;
    private String nikQty;
    private String nikRate;
    private String nikTotal;
    private String remQty;
    private String remTot;
    private String notes;
    private String unitStock;
    private String unitNikasa;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getEntryFormId() {
        return entryFormId;
    }

    public void setEntryFormId(String entryFormId) {
        this.entryFormId = entryFormId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getInQty() {
        return inQty;
    }

    public void setInQty(String inQty) {
        this.inQty = inQty;
    }

    public String getInRate() {
        return inRate;
    }

    public void setInRate(String inRate) {
        this.inRate = inRate;
    }

    public String getInTotal() {
        return inTotal;
    }

    public void setInTotal(String inTotal) {
        this.inTotal = inTotal;
    }

    public String getReqFormId() {
        return reqFormId;
    }

    public void setReqFormId(String reqFormId) {
        this.reqFormId = reqFormId;
    }

    public String getNikasaBranch() {
        return nikasaBranch;
    }

    public void setNikasaBranch(String nikasaBranch) {
        this.nikasaBranch = nikasaBranch;
    }

    public String getNikQty() {
        return nikQty;
    }

    public void setNikQty(String nikQty) {
        this.nikQty = nikQty;
    }

    public String getNikRate() {
        return nikRate;
    }

    public void setNikRate(String nikRate) {
        this.nikRate = nikRate;
    }

    public String getNikTotal() {
        return nikTotal;
    }

    public void setNikTotal(String nikTotal) {
        this.nikTotal = nikTotal;
    }

    public String getRemQty() {
        return remQty;
    }

    public void setRemQty(String remQty) {
        this.remQty = remQty;
    }

    public String getRemTot() {
        return remTot;
    }

    public void setRemTot(String remTot) {
        this.remTot = remTot;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUnitStock() {
        return unitStock;
    }

    public void setUnitStock(String unitStock) {
        this.unitStock = unitStock;
    }

    public String getUnitNikasa() {
        return unitNikasa;
    }

    public void setUnitNikasa(String unitNikasa) {
        this.unitNikasa = unitNikasa;
    }

    public String getKhataPanaNumber() {
        return khataPanaNumber;
    }

    public void setKhataPanaNumber(String khataPanaNumber) {
        this.khataPanaNumber = khataPanaNumber;
    }

    @Override
    public boolean equals(Object obj) {
        ReportBean rb = (ReportBean) obj;
        if (rb.getItemId() == this.getItemId()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ReportBean [itemId=");
        builder.append(itemId);
        builder.append(", date=");
        builder.append(date);
        builder.append(", goodsName=");
        builder.append(goodsName);
        builder.append(", entryFormId=");
        builder.append(entryFormId);
        builder.append(", khataPanaNumber=");
        builder.append(khataPanaNumber);
        builder.append(", supplier=");
        builder.append(supplier);
        builder.append(", specification=");
        builder.append(specification);
        builder.append(", inQty=");
        builder.append(inQty);
        builder.append(", inRate=");
        builder.append(inRate);
        builder.append(", inTotal=");
        builder.append(inTotal);
        builder.append(", reqFormId=");
        builder.append(reqFormId);
        builder.append(", nikasaBranch=");
        builder.append(nikasaBranch);
        builder.append(", nikQty=");
        builder.append(nikQty);
        builder.append(", nikRate=");
        builder.append(nikRate);
        builder.append(", nikTotal=");
        builder.append(nikTotal);
        builder.append(", remQty=");
        builder.append(remQty);
        builder.append(", remTot=");
        builder.append(remTot);
        builder.append(", notes=");
        builder.append(notes);
        builder.append(", unitStock=");
        builder.append(unitStock);
        builder.append(", unitNikasa=");
        builder.append(unitNikasa);
        builder.append("]");
        return builder.toString();
    }

}
