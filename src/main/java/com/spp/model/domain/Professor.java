package com.spp.model.domain;

public class Professor extends User {
    @Override
    public String generateEmail() {
        return String.format("%s@uv.mx", getUsername());
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
}
