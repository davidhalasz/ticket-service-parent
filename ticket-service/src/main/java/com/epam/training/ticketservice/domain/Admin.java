package com.epam.training.ticketservice.domain;

public class Admin {
    private String name;
    private String password;
    private boolean priviliged;

    public Admin(String name, String password, boolean priviliged) {
        this.name = name;
        this.password = password;
        this.priviliged = priviliged;
    }

    public String getName(String name) {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPriviliged() {
        return priviliged;
    }

    public void setPriviliged(boolean priviliged) {
        this.priviliged = priviliged;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", priviliged=" + priviliged +
                '}';
    }
}
