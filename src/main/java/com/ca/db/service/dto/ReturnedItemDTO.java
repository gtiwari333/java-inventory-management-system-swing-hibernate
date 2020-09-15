package com.ca.db.service.dto;

public class ReturnedItemDTO {

    public final int qty;
    public final int damageStatus;

    public ReturnedItemDTO(int qty, int damageStatus, String rackNumber) {
        super();
        this.qty = qty;
        this.damageStatus = damageStatus;
    }

}
