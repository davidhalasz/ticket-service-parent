package com.epam.training.ticketservice.dataaccess.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AdminEntity {

    @Id
    private String name;
    private String password;
    @Setter
    private boolean priviliged;

    public AdminEntity(String name, String password, boolean priviliged) {
        this.name = name;
        this.password = password;
        this.priviliged = priviliged;
    }
}
