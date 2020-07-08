package com.spp.model.domain;

public class Professor extends User {
    @Override
    public String generateEmail() {
        return String.format("%s@uv.mx", getUsername());
    }

    @Override
    public boolean disable() {
        return super.disable();
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public final String getUsername() {
        return super.getUsername();
    }

    @Override
    public final void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public final String getPassword() {
        return super.getPassword();
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

    @Override
    public final void setUserType(String userType) {
        super.setUserType(userType);
    }

    @Override
    public final String getUserType() {
        return super.getUserType();
    }

    @Override
    public final void setActive(boolean active) {
        super.setActive(active);
    }

    @Override
    public final boolean isActive() {
        return super.isActive();
    }
}
