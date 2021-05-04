package com.epam.training.ticketservice.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
public class Screening {

    private UUID uuid;
    private Movie movie;
    private Room room;
    private LocalDateTime startDate;

    public Screening(Movie movie, Room room, LocalDateTime startDate) {
        this.uuid = null;
        this.movie = movie;
        this.room = room;
        this.startDate = startDate;
    }

    public Screening(UUID uuid, Movie movie, Room room, LocalDateTime startDate) {
        this.uuid = uuid;
        this.movie = movie;
        this.room = room;
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return movie.getTitle() + " ("
                + movie.getGenre() + ", "
                + movie.getRuntime() + " minutes), "
                + "screened in room "
                + room.getName() + ", at "
                + startDate.format(formatter);
    }
}
