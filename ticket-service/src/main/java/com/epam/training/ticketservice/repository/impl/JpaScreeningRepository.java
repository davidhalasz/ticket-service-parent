package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.ScreeningDao;
import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.dataaccess.entity.RoomEntity;
import com.epam.training.ticketservice.dataaccess.entity.ScreeningEntity;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.repository.MapperRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaScreeningRepository implements ScreeningRepository {

    private ScreeningDao screeningDao;
    private MapperRepository mapperRepository;

    public JpaScreeningRepository(ScreeningDao screeningDao, MapperRepository mapperRepository) {
        this.mapperRepository = mapperRepository;
        this.screeningDao = screeningDao;
    }

    @Override
    public void createScreening(Movie movie, Room room, LocalDateTime startDateTime) {
        ScreeningEntity screeningEntity = mapperRepository.mapperScreening(movie, room, startDateTime);
        screeningDao.save(screeningEntity);
    }

    @Override
    public List<Screening> getAllScreening() {
        List<ScreeningEntity> screeningEntities = screeningDao.findAll();
        return mapScreeningEntities(screeningEntities);
    }

    private List<Screening> mapScreeningEntities(List<ScreeningEntity> screeningEntities) {
        return screeningEntities.stream()
                .map(this::mapScreeningEntity)
                .collect(Collectors.toList());
    }

    private Screening mapScreeningEntity(ScreeningEntity screeningEntity) {
        return new Screening(mapMovieEntity(screeningEntity.getMovieEntity()),
                mapRoomEntity(screeningEntity.getRoomEntity()),
                screeningEntity.getDateTime());
    }

    private Movie mapMovieEntity(MovieEntity movieEntity) {
        return new Movie(movieEntity.getTitle(), movieEntity.getGenre(), movieEntity.getRuntime());
    }

    private Room mapRoomEntity(RoomEntity roomEntity) {
        return new Room(roomEntity.getName(), roomEntity.getRows(), roomEntity.getColumns());
    }



}
