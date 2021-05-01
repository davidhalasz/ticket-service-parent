package com.epam.training.ticketservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class AdminEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String password;
    private boolean priviliged;


    public AdminEntity(String name, String password, boolean priviliged) {
        this.name = name;
        this.password = password;
        this.priviliged = priviliged;
    }

    public String getName() {
        return name;
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
}
