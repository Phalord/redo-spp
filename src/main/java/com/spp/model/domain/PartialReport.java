package com.spp.model.domain;

import java.sql.Timestamp;
import java.util.List;

public class PartialReport extends Report {
    private String partialPeriod;
    private short projectHoursCovered;
    private byte reportNumber;

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

    @Override
    public final void generateFolio(Timestamp timestamp, String studentEnrollment) {
        super.generateFolio(timestamp, studentEnrollment);
    }

    public void setPartialPeriod(String partialPeriod) {
        this.partialPeriod = partialPeriod;
    }

    public String getPartialPeriod() {
        return partialPeriod;
    }

    public final void setProjectHoursCovered(short projectHoursCovered) {
        this.projectHoursCovered = projectHoursCovered;
    }

    public final short getProjectHoursCovered() {
        return projectHoursCovered;
    }

    public final void setReportNumber(byte reportNumber) {
        this.reportNumber = reportNumber;
    }

    public final byte getReportNumber() {
        return reportNumber;
    }
}
