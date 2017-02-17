package com.ca.db.service.dto;

public class ReturnedItemDTO {

    public int qty;
    public int damageStatus;
    public String rackNumber;

    public ReturnedItemDTO(int qty, int damageStatus, String rackNumber) {
        super();
        this.qty = qty;
        this.damageStatus = damageStatus;
        this.rackNumber = rackNumber;
    }

}
