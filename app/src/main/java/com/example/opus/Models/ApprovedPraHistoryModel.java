package com.example.opus.Models;

public class ApprovedPraHistoryModel {
    private String processBy;
    private String date;
    private String Remark;
    private  String nextProcessBy;

    public String getProcessBy() {
        return processBy;
    }

    public void setProcessBy(String processBy) {
        this.processBy = processBy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getNextProcessBy() {
        return nextProcessBy;
    }

    public void setNextProcessBy(String nextProcessBy) {
        this.nextProcessBy = nextProcessBy;
    }
}
