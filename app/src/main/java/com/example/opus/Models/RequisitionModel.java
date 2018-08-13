package com.example.opus.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class RequisitionModel implements Serializable {
    private String dept;
    private String empCode;
    private String empName;
    private String userID;
    private String userName;
    private ArrayList<ProjectNames> projects = new ArrayList<>();

    public RequisitionModel()  {

    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<ProjectNames> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<ProjectNames> projects) {
        this.projects = projects;
    }

    public void addProject(String projectID, String projectName) {
        ProjectNames project = new ProjectNames(projectID, projectName);
        projects.add(project);
    }

    public class ProjectNames implements Serializable {
        public String id;
        public String projectName;

        public ProjectNames(String id, String projectName) {
            this.id = id;
            this.projectName = projectName;
        }
    }
}
