package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RepositoryException.*;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

class ScreeningServiceTest {

    private static final String TITLE = "movie title";
    private static final String NAME = "room name";
    private static final LocalDateTime DATETIME = LocalDateTime.now();
    private static final Movie MOVIE =  new Movie(TITLE, "genre", 30);
    private static final Room ROOM = new Room(NAME, 10,10);
    private static final Screening SCREENING = new Screening(MOVIE, ROOM, DATETIME);


    private ScreeningService underTest;

    @Mock
    private ScreeningService screeningService;

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new ScreeningService(screeningRepository, movieRepository, roomRepository);
    }


    @Test
    public void testCreateScreeningShouldReturnExceptionWhenThereIsOverlapping()
            throws OverlappingException,
            RoomNotFoundException,
            MovieNotFoundException,
            OverlappingInBreakException {
        // Given
        doThrow(OverlappingException.class)
                .when(screeningService)
                .createScreening(anyString(), anyString(), any());

        // Then
        assertThrows(OverlappingException.class, () ->{
            // When
            screeningService.createScreening(TITLE, NAME, DATETIME);
        });
    }

    @Test
    public void testDeleteScreeningReturnsExceptionWhenScreeningNitExists() throws ScreeningNotFoundException {
        // Given
        doThrow(ScreeningNotFoundException.class)
                .when(screeningRepository)
                .deleteScreening(anyString(), anyString(), any());

        // Then
        assertThrows(ScreeningNotFoundException.class, () -> {
            // When
            screeningRepository.deleteScreening(TITLE, NAME, DATETIME);
        });
    }
}