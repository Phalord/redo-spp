package com.spp.model.domain;

import java.sql.Timestamp;

public class Activity {
    private int activityID;
    private Professor createdBy;
    private Practitioner deliveredBy;
    private String title;
    private String description;
    private Timestamp deliveredAt;
    private Timestamp dueDate;
    private short estimatedCompletionHours;
    private short actualCompletionHours;
    private int ReportID;

    public final void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public final int getActivityID() {
        return activityID;
    }

    public final void setCreatedBy(Professor createdBy) {
        this.createdBy = createdBy;
    }

    public final Professor getCreatedBy() {
        return createdBy;
    }

    public final void setDeliveredBy(Practitioner deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public final Practitioner getDeliveredBy() {
        return deliveredBy;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public final String getTitle() {
        return title;
    }

    public final void setDescription(String description) {
        this.description = description;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDeliveredAt(Timestamp deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public final Timestamp getDeliveredAt() {
        return deliveredAt;
    }

    public final void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public final Timestamp getDueDate() {
        return dueDate;
    }

    public final void setEstimatedCompletionHours(short estimatedCompletionHours) {
        this.estimatedCompletionHours = estimatedCompletionHours;
    }

    public final short getEstimatedCompletionHours() {
        return estimatedCompletionHours;
    }

    public final void setActualCompletionHours(short actualCompletionHours) {
        this.actualCompletionHours = actualCompletionHours;
    }

    public final short getActualCompletionHours() {
        return actualCompletionHours;
    }

    public final void setReportID(int reportID) {
        ReportID = reportID;
    }

    public final int getReportID() {
        return ReportID;
    }
}
