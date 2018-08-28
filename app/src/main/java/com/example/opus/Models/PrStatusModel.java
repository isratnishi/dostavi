package com.example.opus.Models;

public class PrStatusModel {
    private String empName;
    private String entryTime;
    private String aprovalStatus;
    private String nextEmpName;

    public PrStatusModel() {
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getAprovalStatus() {
        return aprovalStatus;
    }

    public void setAprovalStatus(String aprovalStatus) {
        this.aprovalStatus = aprovalStatus;
    }

    public String getNextEmpName() {
        return nextEmpName;
    }

    public void setNextEmpName(String nextEmpName) {
        this.nextEmpName = nextEmpName;
    }
}
