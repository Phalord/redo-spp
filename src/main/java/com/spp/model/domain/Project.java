package com.spp.model.domain;

public class Project {
    private int projectID;
    private RelatedCompany requestedBy;
    private ProjectAssignment projectAssignment;
    private String title;
    private String description;
    private String resources;

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setRequestedBy(RelatedCompany requestedBy) {
        this.requestedBy = requestedBy;
    }

    public RelatedCompany getRequestedBy() {
        return requestedBy;
    }

    public void setProjectAssignment(ProjectAssignment projectAssignment) {
        this.projectAssignment = projectAssignment;
    }

    public ProjectAssignment getProjectAssignment() {
        return projectAssignment;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getResources() {
        return resources;
    }
}
