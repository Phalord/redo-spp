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

    public final void setRelatedCompanyID(int relatedCompanyID) {
        this.relatedCompanyID = relatedCompanyID;
    }

    public final int getRelatedCompanyID() {
        return relatedCompanyID;
    }

    public final void setEmployee(ProjectResponsible employee) {
        this.employee = employee;
    }

    public final ProjectResponsible getEmployee() {
        return employee;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public final void setAddress(String address) {
        this.address = address;
    }

    public final String getAddress() {
        return address;
    }

    public final void setCity(String city) {
        this.city = city;
    }

    public final String getCity() {
        return city;
    }

    public final void setEmail(String email) {
        this.email = email;
    }

    public final String getEmail() {
        return email;
    }

    public final void setPhone(String phone) {
        this.phone = phone;
    }

    public final String getPhone() {
        return phone;
    }

    public final void setSector(String sector) {
        this.sector = sector;
    }

    public final String getSector() {
        return sector;
    }

    public final void setState(String state) {
        this.state = state;
    }

    public final String getState() {
        return state;
    }

    @Override
    public final String toString() {
        return name;
    }
}
