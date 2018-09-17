
package com.opus_bd.dostavi.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IOUItemModel implements Serializable
{

    @SerializedName("IOUMasterID")
    @Expose
    private Integer iOUMasterID;
    @SerializedName("ReqID")
    @Expose
    private Integer reqID;
    @SerializedName("ItemID")
    @Expose
    private Integer itemID;
    @SerializedName("ProjectID")
    @Expose
    private Integer projectID;
    @SerializedName("Rate")
    @Expose
    private Double rate;
    @SerializedName("Qty")
    @Expose
    private Double qty;
    @SerializedName("IOU_DStatus")
    @Expose
    private Integer iOUDStatus;
    @SerializedName("GRQty")
    @Expose
    private Double gRQty;
    @SerializedName("ItemName")
    @Expose
    private String itemName;
    @SerializedName("UnitName")
    @Expose
    private String unitName;
    @SerializedName("TotalQty")
    @Expose
    private Double totalQty;
    @SerializedName("TotalAmn")
    @Expose
    private Double totalAmn;
    @SerializedName("TotalProcureQty")
    @Expose
    private Double totalProcureQty;
    @SerializedName("LastRate")
    @Expose
    private Double lastRate;
    @SerializedName("ReqDetailID")
    @Expose
    private Object reqDetailID;
    @SerializedName("UpdatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("UpdatedDate")
    @Expose
    private Object updatedDate;
    @SerializedName("ItemSpac")
    @Expose
    private String itemSpac;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    private final static long serialVersionUID = 1163989396613394072L;

    public Integer getIOUMasterID() {
        return iOUMasterID;
    }

    public void setIOUMasterID(Integer iOUMasterID) {
        this.iOUMasterID = iOUMasterID;
    }

    public Integer getReqID() {
        return reqID;
    }

    public void setReqID(Integer reqID) {
        this.reqID = reqID;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Integer getIOUDStatus() {
        return iOUDStatus;
    }

    public void setIOUDStatus(Integer iOUDStatus) {
        this.iOUDStatus = iOUDStatus;
    }

    public Double getGRQty() {
        return gRQty;
    }

    public void setGRQty(Double gRQty) {
        this.gRQty = gRQty;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Double getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Double totalQty) {
        this.totalQty = totalQty;
    }

    public Double getTotalAmn() {
        return totalAmn;
    }

    public void setTotalAmn(Double totalAmn) {
        this.totalAmn = totalAmn;
    }

    public Double getTotalProcureQty() {
        return totalProcureQty;
    }

    public void setTotalProcureQty(Double totalProcureQty) {
        this.totalProcureQty = totalProcureQty;
    }

    public Double getLastRate() {
        return lastRate;
    }

    public void setLastRate(Double lastRate) {
        this.lastRate = lastRate;
    }

    public Object getReqDetailID() {
        return reqDetailID;
    }

    public void setReqDetailID(Object reqDetailID) {
        this.reqDetailID = reqDetailID;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getItemSpac() {
        return itemSpac;
    }

    public void setItemSpac(String itemSpac) {
        this.itemSpac = itemSpac;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

}
