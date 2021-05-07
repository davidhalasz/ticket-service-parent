package com.epam.training.ticketservice.repository.mapper;

import com.epam.training.ticketservice.dataaccess.entity.AdminEntity;
import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.dataaccess.entity.RoomEntity;
import com.epam.training.ticketservice.dataaccess.entity.ScreeningEntity;
import com.epam.training.ticketservice.domain.Admin;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Mapper implements MapperInterface {

    @Override
    public Admin mapAdmin(AdminEntity adminEntity) {
        return new Admin(adminEntity.getName(),
                adminEntity.getPassword(),
                adminEntity.isPriviliged());
    }

    @Override
    public MovieEntity mapperMovie(Movie movie) {
        return new MovieEntity(movie.getTitle(), movie.getGenre(), movie.getRuntime());
    }

    @Override
    public Movie mapMovieEntity(MovieEntity movieEntity) {
        return new Movie(movieEntity.getTitle(), movieEntity.getGenre(), movieEntity.getRuntime());
    }

    @Override
    public List<Movie> mapMovieEntities(List<MovieEntity> movieEntities) {
        return movieEntities.stream()
                .map(this::mapMovieEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RoomEntity mapperRoom(Room room) {
        return new RoomEntity(room.getName(), room.getRows(), room.getColumns());
    }

    @Override
    public Room mapRoomEntity(RoomEntity roomEntity) {
        return new Room(roomEntity.getName(), roomEntity.getRows(), roomEntity.getColumns());
    }

    @Override
    public List<Room> mapRoomEntities(List<RoomEntity> roomEntities) {
        return roomEntities.stream()
                .map(this::mapRoomEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ScreeningEntity mapperScreening(Movie movie, Room room, LocalDateTime startDateTime) {
        return new ScreeningEntity(mapperMovie(movie), mapperRoom(room), startDateTime);
    }

    @Override
    public List<Screening> mapScreeningEntities(List<ScreeningEntity> screeningEntities) {
        return screeningEntities.stream()
                .map(this::mapScreeningEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Screening mapScreeningEntity(ScreeningEntity screeningEntity) {
        return new Screening(mapMovieEntity(screeningEntity.getMovieEntity()),
                mapRoomEntity(screeningEntity.getRoomEntity()),
                screeningEntity.getDateTime());
    }
}
