
package com.opus_bd.dostavi.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IOUApprovalModel implements Serializable
{

    @SerializedName("IOUNo")
    @Expose
    private String iOUNo;
    @SerializedName("itemNameTextView")
    @Expose
    private String iOUDate;
    @SerializedName("ReqID")
    @Expose
    private Integer reqID;
    @SerializedName("UserID")
    @Expose
    private Object userID;
    @SerializedName("ProjectID")
    @Expose
    private Integer projectID;
    @SerializedName("IOUStatus")
    @Expose
    private Integer iOUStatus;
    @SerializedName("AttentionTo")
    @Expose
    private Object attentionTo;
    @SerializedName("EntryTime")
    @Expose
    private Object entryTime;
    @SerializedName("StatusDesc")
    @Expose
    private String statusDesc;
    @SerializedName("IOUValue")
    @Expose
    private Double iOUValue;
    @SerializedName("ReqNo")
    @Expose
    private Object reqNo;
    @SerializedName("TargetDate")
    @Expose
    private String targetDate;
    @SerializedName("ReqDate")
    @Expose
    private Object reqDate;
    @SerializedName("PrIOUNo")
    @Expose
    private String prIOUNo;
    @SerializedName("ProjectName")
    @Expose
    private Object projectName;
    @SerializedName("Remarks")
    @Expose
    private Object remarks;
    private final static long serialVersionUID = 6606180279847500918L;

    public String getIOUNo() {
        return iOUNo;
    }

    public void setIOUNo(String iOUNo) {
        this.iOUNo = iOUNo;
    }

    public String getIOUDate() {
        return iOUDate;
    }

    public void setIOUDate(String iOUDate) {
        this.iOUDate = iOUDate;
    }

    public Integer getReqID() {
        return reqID;
    }

    public void setReqID(Integer reqID) {
        this.reqID = reqID;
    }

    public Object getUserID() {
        return userID;
    }

    public void setUserID(Object userID) {
        this.userID = userID;
    }

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public Integer getIOUStatus() {
        return iOUStatus;
    }

    public void setIOUStatus(Integer iOUStatus) {
        this.iOUStatus = iOUStatus;
    }

    public Object getAttentionTo() {
        return attentionTo;
    }

    public void setAttentionTo(Object attentionTo) {
        this.attentionTo = attentionTo;
    }

    public Object getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Object entryTime) {
        this.entryTime = entryTime;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Double getIOUValue() {
        return iOUValue;
    }

    public void setIOUValue(Double iOUValue) {
        this.iOUValue = iOUValue;
    }

    public Object getReqNo() {
        return reqNo;
    }

    public void setReqNo(Object reqNo) {
        this.reqNo = reqNo;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public Object getReqDate() {
        return reqDate;
    }

    public void setReqDate(Object reqDate) {
        this.reqDate = reqDate;
    }

    public String getPrIOUNo() {
        return prIOUNo;
    }

    public void setPrIOUNo(String prIOUNo) {
        this.prIOUNo = prIOUNo;
    }

    public Object getProjectName() {
        return projectName;
    }

    public void setProjectName(Object projectName) {
        this.projectName = projectName;
    }

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

}
