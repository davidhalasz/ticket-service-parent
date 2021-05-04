package com.epam.training.ticketservice.domain;

import lombok.Getter;

@Getter
public class Admin {
    private final String name;
    private final String password;
    private final boolean priviliged;

    public Admin(String name, String password, boolean priviliged) {
        this.name = name;
        this.password = password;
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
