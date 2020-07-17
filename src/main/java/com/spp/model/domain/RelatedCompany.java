package com.spp.model.domain;

public class RelatedCompany {
    private int relatedCompanyID;
    private ProjectResponsible employee;
    private String name;
    private String address;
    private String city;
    private String email;
    private String phone;
    private String sector;
    private String state;

    public void setRelatedCompanyID(int relatedCompanyID) {
        this.relatedCompanyID = relatedCompanyID;
    }

    public int getRelatedCompanyID() {
        return relatedCompanyID;
    }

    public void setEmployee(ProjectResponsible employee) {
        this.employee = employee;
    }

    public ProjectResponsible getEmployee() {
        return employee;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSector() {
        return sector;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return name;
    }
}
