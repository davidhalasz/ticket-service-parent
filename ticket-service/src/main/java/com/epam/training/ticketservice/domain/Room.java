package com.epam.training.ticketservice.domain;

import lombok.Getter;

@Getter
public class Room {
    private String name;
    private int rows;
    private int columns;

    public Room(String name, int rows, int columns) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Room " + name + " with "
                + rows*columns + " seats, "
                + rows + " rows and "
                + columns + " columns\n";
    }
}
