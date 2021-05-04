package com.epam.training.ticketservice.dataaccess.entity;


import com.epam.training.ticketservice.domain.Screening;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

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
