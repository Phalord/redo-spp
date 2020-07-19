package com.spp.model.domain;

public class Practitioner extends User {
    private ProjectAssignment projectAssignment;
    private String shift;
    private int groupID;

    public Practitioner() {
        super();
        setShift("Turno");
    }

    @Override
    public String generateEmail() {
        return String.format("%s@estudiantes.mx", getUsername());
    }

    @Override
    public void disable() {
        super.disable();
    }

    @Override
    public final void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public final void setName(String name) {
        super.setName(name);
    }

    @Override
    public final String getName() {
        return super.getName();
    }

    @Override
    public final void setSurnames(String surnames) {
        super.setSurnames(surnames);
    }

    @Override
    public final String getSurnames() {
        return super.getSurnames();
    }

    public final void setShift(String shift) {
        this.shift = shift;
    }

    public final String getShift() {
        return shift;
    }

    public final void setProjectAssignment(ProjectAssignment projectAssignment) {
        this.projectAssignment = projectAssignment;
    }

    public final ProjectAssignment getProjectAssignment() {
        return projectAssignment;
    }

    public final void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public final int getGroupID() {
        return groupID;
    }
}
