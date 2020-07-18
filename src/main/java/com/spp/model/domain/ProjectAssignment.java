package com.spp.model.domain;

import java.sql.Timestamp;

public class ProjectAssignment {
    private int projectAssignmentID;
    private Coordinator assignedBy;
    private Practitioner practitioner;
    private Project project;
    private Timestamp createdAt;
    private short grade;
    private String status;

    public final void setProjectAssignmentID(int projectAssignmentID) {
        this.projectAssignmentID = projectAssignmentID;
    }

    public final int getProjectAssignmentID() {
        return projectAssignmentID;
    }

    public void setAssignedBy(Coordinator assignedBy) {
        this.assignedBy = assignedBy;
    }

    public Coordinator getAssignedBy() {
        return assignedBy;
    }

    public final void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }

    public final Practitioner getPractitioner() {
        return practitioner;
    }

    public final void setProject(Project project) {
        this.project = project;
    }

    public final Project getProject() {
        return project;
    }

    public final void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public final Timestamp getCreatedAt() {
        return createdAt;
    }

    public final void setGrade(short grade) {
        this.grade = grade;
    }

    public final short getGrade() {
        return grade;
    }

    public final void setStatus(String status) {
        this.status = status;
    }

    public final String getStatus() {
        return status;
    }
}
