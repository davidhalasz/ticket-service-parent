package com.epam.training.ticketservice.dataaccess.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class RoomEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private int rows;
    private int columns;

    public RoomEntity(String name, int rows, int columns) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Room " + name + " with "
                + rows*columns + " seats, "
                + rows + " rows and "
                + columns + " columns";
    }
}
