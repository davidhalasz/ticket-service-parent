package com.epam.training.ticketservice.dataaccess.entity;

import com.epam.training.ticketservice.repository.RoomRepository;
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
public class RoomEntity {

    @Id
    private String name;
    @Setter
    private int rows;
    @Setter
    private int columns;

    public RoomEntity(String name, int rows, int columns) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }
}
