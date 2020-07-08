package com.spp.model.domain;

import java.sql.Timestamp;

public class Activity {
    private int activityID;
    private Professor creator;
    private Practitioner deliveredBy;
    private String title;
    private String description;
    private Timestamp deliveredAt;
    private short estimatedCompletionHours;
    private short actualCompletionHours;

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setCreator(Professor creator) {
        this.creator = creator;
    }

    public Professor getCreator() {
        return creator;
    }

    public void setDeliveredBy(Practitioner deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public Practitioner getDeliveredBy() {
        return deliveredBy;
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

    public void setDeliveredAt(Timestamp deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public Timestamp getDeliveredAt() {
        return deliveredAt;
    }

    public void setEstimatedCompletionHours(short estimatedCompletionHours) {
        this.estimatedCompletionHours = estimatedCompletionHours;
    }

    public short getEstimatedCompletionHours() {
        return estimatedCompletionHours;
    }

    public void setActualCompletionHours(short actualCompletionHours) {
        this.actualCompletionHours = actualCompletionHours;
    }

    public short getActualCompletionHours() {
        return actualCompletionHours;
    }
}
