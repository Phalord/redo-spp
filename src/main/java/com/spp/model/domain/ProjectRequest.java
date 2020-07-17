package com.spp.model.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProjectRequest {
    private int projectRequestID;
    private final List<Project> projectOptions = new ArrayList<>(3);
    private Timestamp requestedAt;
    private String requestedBy;
    private boolean pending;

    public final void setProjectRequestID(int projectRequestID) {
        this.projectRequestID = projectRequestID;
    }

    public final int getProjectRequestID() {
        return projectRequestID;
    }

    public final void setRequestedAt(Timestamp requestedAt) {
        this.requestedAt = requestedAt;
    }

    public final Timestamp getRequestedAt() {
        return requestedAt;
    }

    public final void setPending(boolean pending) {
        this.pending = pending;
    }

    public final boolean isPending() {
        return pending;
    }

    public final void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public final String getRequestedBy() {
        return requestedBy;
    }

    public final List<Project> getProjectOptions() {
        return projectOptions;
    }
}
