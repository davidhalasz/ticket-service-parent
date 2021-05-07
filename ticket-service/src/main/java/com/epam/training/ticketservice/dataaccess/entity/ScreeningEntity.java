package com.epam.training.ticketservice.dataaccess.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Column;

import java.time.LocalDateTime;
import java.util.UUID;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"room_id", "date_time"})})
@Entity
@Getter
public class ScreeningEntity {

    @Id
    @GeneratedValue
    private UUID uuid;
    @Setter
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private MovieEntity movieEntity;
    @Setter
    @ManyToOne
    @JoinColumn(name = "room_id")
    private  RoomEntity roomEntity;
    @Setter
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

}
