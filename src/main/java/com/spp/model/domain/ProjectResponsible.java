package com.spp.model.domain;

public class ProjectResponsible {
    private int projectResponsibleID;
    private String email;
    private String name;
    private String surname;
    private String phone;
    private int relatedCompanyID;

    public void setProjectResponsibleID(int projectResponsibleID) {
        this.projectResponsibleID = projectResponsibleID;
    }

    public int getProjectResponsibleID() {
        return projectResponsibleID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setRelatedCompanyID(int relatedCompanyID) {
        this.relatedCompanyID = relatedCompanyID;
    }

    public int getRelatedCompanyID() {
        return relatedCompanyID;
    }
}
