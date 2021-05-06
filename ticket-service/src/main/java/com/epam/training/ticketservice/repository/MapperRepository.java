package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.dataaccess.entity.RoomEntity;
import com.epam.training.ticketservice.dataaccess.entity.ScreeningEntity;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;

import java.time.LocalDateTime;
import java.util.List;

public interface MapperRepository {

    MovieEntity mapperMovie(Movie movie);

    RoomEntity mapperRoom(Room room);

    ScreeningEntity mapperScreening(Movie movie, Room room, LocalDateTime startDateTime);

    Movie mapMovieEntity(MovieEntity movieEntity);

    List<Movie> mapMovieEntities(List<MovieEntity> movieEntities);

    List<Room> mapRoomEntities(List<RoomEntity> roomEntities);

    Room mapRoomEntity(RoomEntity roomEntity);
}
