package com.spp.model.domain;

import java.sql.Timestamp;

public class ProjectAssignment {
    private int projectAssignmentID;
    private Practitioner practitioner;
    private Project project;
    private Timestamp createdAt;
    private short grade;
    private String status;

    public void setProjectAssignmentID(int projectAssignmentID) {
        this.projectAssignmentID = projectAssignmentID;
    }

    public int getProjectAssignmentID() {
        return projectAssignmentID;
    }

    public void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }

    public Practitioner getPractitioner() {
        return practitioner;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setGrade(short grade) {
        this.grade = grade;
    }

    public short getGrade() {
        return grade;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
