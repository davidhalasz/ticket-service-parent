package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.ScreeningDao;
import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.dataaccess.entity.RoomEntity;
import com.epam.training.ticketservice.dataaccess.entity.ScreeningEntity;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.repository.mapper.MapperInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

@ExtendWith(MockitoExtension.class)
class JpaScreeningRepositoryTest {

    @InjectMocks
    JpaScreeningRepository screeningRepository;

    @Mock
    ScreeningDao screeningDao;

    @Mock
    MapperInterface mapper;

    private static final String TITLE = "title";
    private static final String GENRE = "genre";
    private static final int RUNTIME = 10;
    private static final Movie MOVIE = new Movie(TITLE, GENRE, RUNTIME);
    private static final String NAME = "name";
    private static final int ROWS = 10;
    private static final int COLUMNS = 10;
    private static final Room ROOM = new Room(NAME, ROWS, COLUMNS);
    private static final String STR_DATETIME = "2021-05-06 10:00";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final LocalDateTime DATETIME = LocalDateTime.parse(STR_DATETIME, dateTimeFormatter);
    private static final Screening SCREENING = new Screening(MOVIE, ROOM, DATETIME);

    private static final MovieEntity MOVIE_ENTITY = new MovieEntity(TITLE, GENRE, RUNTIME);
    private static final RoomEntity ROOM_ENTITY = new RoomEntity(NAME, ROWS, COLUMNS);
    private static final ScreeningEntity SCREENING_ENTITY = new ScreeningEntity(MOVIE_ENTITY, ROOM_ENTITY, DATETIME);
    private static final List<Screening> SCREENINGS = List.of(SCREENING, SCREENING);
    private static final List<ScreeningEntity> SCREENING_ENTITIES = List.of(SCREENING_ENTITY, SCREENING_ENTITY);

    @Test
    void testCreateScreeningShouldBeSuccess() {
        // Given
        given(mapper.mapperScreening(MOVIE, ROOM, DATETIME)).willReturn(SCREENING_ENTITY);

        // When
        screeningRepository.createScreening(MOVIE, ROOM, DATETIME);

        // Then
        verify(screeningDao, times(1)).save(SCREENING_ENTITY);
    }

    @Test
    void testGetAllScreeningsShouldReturnListOfScreenings() {
        // Given
        given(screeningDao.findAll()).willReturn(SCREENING_ENTITIES);
        given(mapper.mapScreeningEntities(SCREENING_ENTITIES)).willReturn(SCREENINGS);

        // When
        List<Screening> actualResult = screeningRepository.getAllScreenings();

        // Then
        assertThat(actualResult, equalTo(SCREENINGS));
    }
}