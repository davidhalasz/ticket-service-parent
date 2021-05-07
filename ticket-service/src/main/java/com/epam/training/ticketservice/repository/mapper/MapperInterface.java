package com.epam.training.ticketservice.repository.mapper;

import com.epam.training.ticketservice.dataaccess.entity.AdminEntity;
import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.dataaccess.entity.RoomEntity;
import com.epam.training.ticketservice.dataaccess.entity.ScreeningEntity;
import com.epam.training.ticketservice.domain.Admin;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;

import java.time.LocalDateTime;
import java.util.List;

public interface MapperInterface {

    Admin mapAdmin(AdminEntity adminEntity);

    MovieEntity mapperMovie(Movie movie);

    Movie mapMovieEntity(MovieEntity movieEntity);

    List<Movie> mapMovieEntities(List<MovieEntity> movieEntities);

    RoomEntity mapperRoom(Room room);

    Room mapRoomEntity(RoomEntity roomEntity);

    List<Room> mapRoomEntities(List<RoomEntity> roomEntities);

    ScreeningEntity mapperScreening(Movie movie, Room room, LocalDateTime startDateTime);

    List<Screening> mapScreeningEntities(List<ScreeningEntity> screeningEntities);

    Screening mapScreeningEntity(ScreeningEntity screeningEntity);
}
