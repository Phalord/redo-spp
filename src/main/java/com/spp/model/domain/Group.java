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

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public void setLecturer(Professor lecturer) {
        this.lecturer = lecturer;
    }

    public Professor getLecturer() {
        return lecturer;
    }

    public void setStudents(List<Practitioner> students) {
        this.students = students;
    }

    public List<Practitioner> getStudents() {
        return students;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setEducationalExperience(String educationalExperience) {
        this.educationalExperience = educationalExperience;
    }

    public String getEducationalExperience() {
        return educationalExperience;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getNrc() {
        return nrc;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getShift() {
        return shift;
    }

    public void setQuota(byte quota) {
        this.quota = quota;
    }

    public byte getQuota() {
        return quota;
    }

    public void setWeeklyHours(byte weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public byte getWeeklyHours() {
        return weeklyHours;
    }
}
