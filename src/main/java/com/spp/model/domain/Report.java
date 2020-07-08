package com.spp.model.domain;

import java.sql.Timestamp;
import java.util.List;

public abstract class Report {
    private int reportID;
    private List<Activity> activities;
    private String reportType;
    private String teacherObservations;
    private Timestamp reportedAt;
    private byte grade;

    protected void setReportID(int reportID) {
        this.reportID = reportID;
    }

    protected int getReportID() {
        return reportID;
    }

    protected void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    protected List<Activity> getActivities() {
        return activities;
    }

    protected void setReportType(String reportType) {
        this.reportType = reportType;
    }

    protected String getReportType() {
        return reportType;
    }

    protected void setTeacherObservations(String teacherObservations) {
        this.teacherObservations = teacherObservations;
    }

    protected String getTeacherObservations() {
        return teacherObservations;
    }

    protected void setReportedAt(Timestamp reportedAt) {
        this.reportedAt = reportedAt;
    }

    protected Timestamp getReportedAt() {
        return reportedAt;
    }

    protected void setGrade(byte grade) {
        this.grade = grade;
    }

    protected byte getGrade() {
        return grade;
    }
}
