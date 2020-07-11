package com.spp.model.domain;

import java.sql.Timestamp;
import java.util.List;

public class MonthlyReport extends Report {
    private List<String> activitiesTitle;
    private List<String> activitiesDescriptionByPractitioner;

    public MonthlyReport() {

    }

    @Override
    public final void setReportID(int reportID) {
        super.setReportID(reportID);
    }

    @Override
    public final int getReportID() {
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

    public void setActivitiesTitle(List<String> activitiesTitle) {
        this.activitiesTitle = activitiesTitle;
    }

    public List<String> getActivitiesTitle() {
        return activitiesTitle;
    }
}