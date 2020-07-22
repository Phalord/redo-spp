package com.spp.model.domain;

import java.sql.Timestamp;
import java.util.List;

public class MonthlyReport extends Report {
    private List<String> activitiesDescription;

    public MonthlyReport() {

    }

    @Override
    public final void setReportID(String reportID) {
        super.setReportID(reportID);
    }

    @Override
    public final String getReportID() {
        return super.getReportID();
    }

    @Override
    public final void setActivities(List<Activity> activities) {
        super.setActivities(activities);
    }

    @Override
    public final List<Activity> getActivities() {
        return super.getActivities();
    }

    @Override
    public final void setReportType(String reportType) {
        super.setReportType(reportType);
    }

    @Override
    public final String getReportType() {
        return super.getReportType();
    }

    @Override
    public final void setTeacherObservations(String teacherObservations) {
        super.setTeacherObservations(teacherObservations);
    }

    @Override
    public final String getTeacherObservations() {
        return super.getTeacherObservations();
    }

    @Override
    public final void setReportedAt(Timestamp reportedAt) {
        super.setReportedAt(reportedAt);
    }

    @Override
    public final Timestamp getReportedAt() {
        return super.getReportedAt();
    }

    @Override
    public final void setGrade(byte grade) {
        super.setGrade(grade);
    }

    @Override
    public final byte getGrade() {
        return super.getGrade();
    }

    public final void setActivitiesDescription(List<String> activitiesDescription) {
        this.activitiesDescription = activitiesDescription;
    }

    public final List<String> getActivitiesDescription() {
        return activitiesDescription;
    }
}
