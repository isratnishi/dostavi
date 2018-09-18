
package com.opus_bd.dostavi.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class POApproveModel implements Serializable
{

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("ReqNo")
    @Expose
    private String reqNo;
    @SerializedName("ReqDate")
    @Expose
    private String reqDate;
    @SerializedName("QuoID")
    @Expose
    private Object quoID;
    @SerializedName("QuoDate")
    @Expose
    private Object quoDate;
    @SerializedName("QuoNO")
    @Expose
    private Object quoNO;
    @SerializedName("CSDate")
    @Expose
    private String cSDate;
    @SerializedName("CSNo")
    @Expose
    private String cSNo;
    @SerializedName("ReqID")
    @Expose
    private Integer reqID;
    @SerializedName("BuyerName")
    @Expose
    private String buyerName;
    @SerializedName("FinalApprover")
    @Expose
    private String finalApprover;
    @SerializedName("ProjectId")
    @Expose
    private Integer projectId;
    private final static long serialVersionUID = -9122996476669985499L;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public Object getQuoID() {
        return quoID;
    }

    public void setQuoID(Object quoID) {
        this.quoID = quoID;
    }

    public Object getQuoDate() {
        return quoDate;
    }

    public void setQuoDate(Object quoDate) {
        this.quoDate = quoDate;
    }

    public Object getQuoNO() {
        return quoNO;
    }

    public void setQuoNO(Object quoNO) {
        this.quoNO = quoNO;
    }

    public String getCSDate() {
        return cSDate;
    }

    public void setCSDate(String cSDate) {
        this.cSDate = cSDate;
    }

    public String getCSNo() {
        return cSNo;
    }

    public void setCSNo(String cSNo) {
        this.cSNo = cSNo;
    }

    public Integer getReqID() {
        return reqID;
    }

    public void setReqID(Integer reqID) {
        this.reqID = reqID;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getFinalApprover() {
        return finalApprover;
    }

    public void setFinalApprover(String finalApprover) {
        this.finalApprover = finalApprover;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

}
