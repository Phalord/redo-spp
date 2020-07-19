package com.spp.model.domain;

public abstract class User {
    private String username;
    private String password;
    private String name;
    private String surnames;
    private String userType;
    private boolean active;

    protected abstract String generateEmail();

    protected void disable() {
        setActive(false);
    }

    public final void setUsername(String username) {
        this.username = username;
    }

    public final String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public final String getPassword() {
        return password;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected String getName() {
        return name;
    }

    protected void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public final void setUserType(String userType) {
        this.userType = userType;
    }

    public final String getUserType() {
        return userType;
    }

    protected String getSurnames() {
        return surnames;
    }

    public final void setActive(boolean active) {
        this.active = active;
    }

    public final boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + ", name=" + name + ", surnames=" + surnames + ", userType=" + userType + ", active=" + active + '}';
    }
}
