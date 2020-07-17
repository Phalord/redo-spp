package com.spp.model.domain;

public abstract class User {
    private String username;
    private String password;
    private String name;
    private String surnames;
    private String userType;
    private boolean active;

    protected abstract String generateEmail();

    protected boolean disable() {
        if (isActive()) {
            setActive(false);
        } else {
            return false;
        }
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
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

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    protected String getSurnames() {
        return surnames;
    }

    protected void setActive(boolean active) {
        this.active = active;
    }

    protected boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + ", name=" + name + ", surnames=" + surnames + ", userType=" + userType + ", active=" + active + '}';
    }
}
