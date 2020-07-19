package com.spp.model.domain;

import java.util.List;

public class Group {
    private int groupID;
    private Professor lecturer;
    private List<Practitioner> students;
    private String educationalExperience;
    private String nrc;
    private String shift;
    private byte quota;
    private byte weeklyHours;

    public final void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public final void setLecturer(Professor lecturer) {
        this.lecturer = lecturer;
    }

    public final Professor getLecturer() {
        return lecturer;
    }

    public final void setStudents(List<Practitioner> students) {
        this.students = students;
    }

    public final List<Practitioner> getStudents() {
        return students;
    }

    public final int getGroupID() {
        return groupID;
    }

    public final void setEducationalExperience(String educationalExperience) {
        this.educationalExperience = educationalExperience;
    }

    public final String getEducationalExperience() {
        return educationalExperience;
    }

    public final void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public final String getNrc() {
        return nrc;
    }

    public final void setShift(String shift) {
        this.shift = shift;
    }

    public final String getShift() {
        return shift;
    }

    public final void setQuota(byte quota) {
        this.quota = quota;
    }

    public final byte getQuota() {
        return quota;
    }

    public final void setWeeklyHours(byte weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public final byte getWeeklyHours() {
        return weeklyHours;
    }
}
