package com.example.opus.models;

import java.io.Serializable;

public class RequisitionMaster implements Serializable {
    private String requisitionNo;
    private String requisitionDate;
    private String targetDate;
    private String projectId;
    private String empCode;
    private String empName;
    private String dept;
    private String draffOrFinal;
    private String subject;
    private String remarks;

    private String compititionWaivers;
    private String postPR;

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

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDraffOrFinal() {
        return draffOrFinal;
    }

    public void setDraffOrFinal(String draffOrFinal) {
        this.draffOrFinal = draffOrFinal;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCompititionWaivers() {
        return compititionWaivers;
    }

    public void setCompititionWaivers(String compititionWaivers) {
        this.compititionWaivers = compititionWaivers;
    }

    public String getPostPR() {
        return postPR;
    }

    public void setPostPR(String postPR) {
        this.postPR = postPR;
    }
}
