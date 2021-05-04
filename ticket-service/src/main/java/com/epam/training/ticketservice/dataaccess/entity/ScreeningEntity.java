package com.epam.training.ticketservice.dataaccess.entity;


import com.epam.training.ticketservice.domain.Screening;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"room_id", "date_time"})})
public class ScreeningEntity {

    @Id
    @GeneratedValue
    private UUID uuid;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    private MovieEntity movieEntity;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private  RoomEntity roomEntity;
    @Column(name = "date_time")
    private LocalDateTime dateTime;


    public ScreeningEntity(MovieEntity movieEntity, RoomEntity roomEntity, LocalDateTime dateTime) {
        this.movieEntity = movieEntity;
        this.roomEntity = roomEntity;
        this.dateTime = dateTime;
    }

    public ScreeningEntity(UUID uuid, MovieEntity movieEntity, RoomEntity roomEntity, LocalDateTime dateTime) {
        this.uuid = uuid;
        this.movieEntity = movieEntity;
        this.roomEntity = roomEntity;
        this.dateTime = dateTime;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public MovieEntity getMovieEntity() {
        return movieEntity;
    }

    public void setMovieEntity(MovieEntity movieEntity) {
        this.movieEntity = movieEntity;
    }

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
