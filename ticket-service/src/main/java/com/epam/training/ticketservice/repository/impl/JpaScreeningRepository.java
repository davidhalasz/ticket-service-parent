package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.ScreeningDao;
import com.epam.training.ticketservice.dataaccess.entity.ScreeningEntity;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.repository.mapper.MapperInterface;
import com.epam.training.ticketservice.exceptions.ScreeningNotFoundException;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Slf4j
public class JpaScreeningRepository implements ScreeningRepository {

    private final ScreeningDao screeningDao;
    private final MapperInterface mapper;

    @Override
    public void createScreening(Movie movie, Room room,
                                LocalDateTime startDateTime) {

        ScreeningEntity screeningEntity = mapper.mapperScreening(movie, room, startDateTime);
        screeningDao.save(screeningEntity);
    }

    @Override
    public void deleteScreening(String movieTitle, String roomName, LocalDateTime startDateTime)
            throws ScreeningNotFoundException {

        ScreeningEntity screeningEntity = screeningDao.findAll().stream()
                .filter(currentScreening -> currentScreening.getMovieEntity().getTitle().equals(movieTitle)
                        && currentScreening.getRoomEntity().getName().equals(roomName)
                        && currentScreening.getDateTime().equals(startDateTime))
                .findFirst()
                .orElseThrow(() -> new ScreeningNotFoundException("There are no screenings"));
        screeningDao.delete(screeningEntity);
    }

    @Override
    public List<Screening> getAllScreenings() {
        List<ScreeningEntity> screeningEntities = screeningDao.findAll();
        return mapper.mapScreeningEntities(screeningEntities);
    }
}
