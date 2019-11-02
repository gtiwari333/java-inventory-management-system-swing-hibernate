package com.ca.ui.report;

public class LedgerReportBean {

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
    private String transferBranch;
    private String nikQty;
    private String nikRate;
    private String nikTotal;
    private String remQty;
    private String remTot;
    private String notes;
    private String unitStock;
    private String unitTransfer;

    private int getItemId() {
        return itemId;
    }

    public final void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public final String getDate() {
        return date;
    }

    public final void setDate(String date) {
        this.date = date;
    }

    public final String getGoodsName() {
        return goodsName;
    }

    public final void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public final String getEntryFormId() {
        return entryFormId;
    }

    public final void setEntryFormId(String entryFormId) {
        this.entryFormId = entryFormId;
    }

    public final String getSupplier() {
        return supplier;
    }

    public final void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public final String getSpecification() {
        return specification;
    }

    public final void setSpecification(String specification) {
        this.specification = specification;
    }

    public final String getInQty() {
        return inQty;
    }

    public final void setInQty(String inQty) {
        this.inQty = inQty;
    }

    public final String getInRate() {
        return inRate;
    }

    public final void setInRate(String inRate) {
        this.inRate = inRate;
    }

    public final String getInTotal() {
        return inTotal;
    }

    public final void setInTotal(String inTotal) {
        this.inTotal = inTotal;
    }

    public final String getReqFormId() {
        return reqFormId;
    }

    public final void setReqFormId(String reqFormId) {
        this.reqFormId = reqFormId;
    }

    public final String getTransferBranch() {
        return transferBranch;
    }

    public final void setTransferBranch(String transferBranch) {
        this.transferBranch = transferBranch;
    }

    public final String getNikQty() {
        return nikQty;
    }

    public final void setNikQty(String nikQty) {
        this.nikQty = nikQty;
    }

    public final String getNikRate() {
        return nikRate;
    }

    public final void setNikRate(String nikRate) {
        this.nikRate = nikRate;
    }

    public final String getNikTotal() {
        return nikTotal;
    }

    public final void setNikTotal(String nikTotal) {
        this.nikTotal = nikTotal;
    }

    public final String getRemQty() {
        return remQty;
    }

    public final void setRemQty(String remQty) {
        this.remQty = remQty;
    }

    public final String getRemTot() {
        return remTot;
    }

    public final void setRemTot(String remTot) {
        this.remTot = remTot;
    }

    public final String getNotes() {
        return notes;
    }

    public final void setNotes(String notes) {
        this.notes = notes;
    }

    public final String getUnitStock() {
        return unitStock;
    }

    public final void setUnitStock(String unitStock) {
        this.unitStock = unitStock;
    }

    public final String getUnitTransfer() {
        return unitTransfer;
    }

    public final void setUnitTransfer(String unitTransfer) {
        this.unitTransfer = unitTransfer;
    }

    public final String getKhataPanaNumber() {
        return khataPanaNumber;
    }

    public final void setKhataPanaNumber(String khataPanaNumber) {
        this.khataPanaNumber = khataPanaNumber;
    }

    @Override
    public final boolean equals(Object obj) {
        LedgerReportBean rb = (LedgerReportBean) obj;
        if (rb.getItemId() == this.getItemId()) {
            return true;
        }
        return false;
    }

    @Override
    public final String toString() {
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
        builder.append(", transferBranch=");
        builder.append(transferBranch);
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
        builder.append(", unitTransfer=");
        builder.append(unitTransfer);
        builder.append("]");
        return builder.toString();
    }

}
