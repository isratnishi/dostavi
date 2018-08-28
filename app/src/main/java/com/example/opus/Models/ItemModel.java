package com.example.opus.Models;

/**
 * Created by Shoukhin on 7/23/2018.
 */

public class ItemModel {
    private String itemCode;
    private String itemName;
    private String unit;
    private String approxPrice;
    private String total;
    private double quantity;
    private double requiredQuantity;
    private String specification;
    private String lastRate;

    public ItemModel() {
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getApproxPrice() {
        return approxPrice;
    }

    public void setApproxPrice(String approxPrice) {
        this.approxPrice = approxPrice;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getLastRate() {
        return lastRate;
    }

    public void setLastRate(String lastRate) {
        this.lastRate = lastRate;
    }

    public double getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }
}
