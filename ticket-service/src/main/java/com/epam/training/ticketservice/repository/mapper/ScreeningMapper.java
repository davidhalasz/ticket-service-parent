package com.epam.training.ticketservice.repository.mapper;

import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.dataaccess.entity.RoomEntity;
import com.epam.training.ticketservice.dataaccess.entity.ScreeningEntity;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.repository.MapperRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class ScreeningMapper implements MapperRepository {

    @Override
    public MovieEntity mapperMovie(Movie movie) {
        return new MovieEntity(movie.getTitle(), movie.getGenre(), movie.getRuntime());
    }

    @Override
    public RoomEntity mapperRoom(Room room) {
        return new RoomEntity(room.getName(), room.getRows(), room.getColumns());
    }

    @Override
    public ScreeningEntity mapperScreening(Movie movie, Room room, LocalDateTime startDateTime) {
        return new ScreeningEntity(mapperMovie(movie), mapperRoom(room), startDateTime);
    }
}
