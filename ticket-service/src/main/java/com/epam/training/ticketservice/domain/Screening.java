package com.epam.training.ticketservice.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

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


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
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
