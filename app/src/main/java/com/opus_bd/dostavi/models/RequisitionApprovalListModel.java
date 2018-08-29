package com.opus_bd.dostavi.models;

import java.io.Serializable;

public class RequisitionApprovalListModel implements Serializable {
    private String requisitionNo;
    private String requisitionDate;
    private  String subject;
    private String department;
    private String requisitionID;
    private String projectID;

    public String getRequisitionNo() {
        return requisitionNo;
    }

    public void setRequisitionNo(String requisitionNo) {
        this.requisitionNo = requisitionNo;
    }

    public String getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(String requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRequisitionID() {
        return requisitionID;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public void setRequisitionID(String requisitionID) {
        this.requisitionID = requisitionID;

    }
}
