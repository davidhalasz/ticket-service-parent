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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ScreeningServiceTest {

    private final static String STR_DATETIME = "2021-05-06 10:00";
    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final static LocalDateTime DATETIME = LocalDateTime.parse(STR_DATETIME, dateTimeFormatter);

    private ScreeningService underTest;
    private static final String TITLE = "movie title";
    private static final String NAME = "room name";
    private static final Movie MOVIE =  new Movie(TITLE, "genre", 60);
    private static final Room ROOM = new Room(NAME, 10,10);
    // screening 10:00 - 11:00
    private static final Screening SCREENING_1 = new Screening(MOVIE, ROOM, DATETIME);
    // screening 13:00 - 14:00
    private static final Screening SCREENING_2 = new Screening(MOVIE, ROOM, DATETIME.plusMinutes(180));
    private static final List<Screening> SCREENINGS = List.of(SCREENING_1, SCREENING_2);

    // screening 11:05 - 12:05
    private static final Screening INV_SCREENING_IN_BREAK = new Screening(MOVIE, ROOM, DATETIME.plusMinutes(125));
    // screening 11:15 - 12:15
    private static final Screening VALID_SCREENING = new Screening(MOVIE, ROOM, DATETIME);
    private final static LocalDateTime START_DATETIME = DATETIME.plusMinutes(-30);
    private final static LocalDateTime END_DATETIME = DATETIME.plusMinutes(30);




    @InjectMocks
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
    void testCreateScreeningIsSuccessfulWhenListOfScreeningsIsEmpty()
            throws MovieNotFoundException, RoomNotFoundException,
            OverlappingException, OverlappingInBreakException {

        // Given
        given(movieRepository.findMovieByTitle(TITLE)).willReturn(MOVIE);
        given(roomRepository.findRoomByName(NAME)).willReturn(ROOM);

        // When
        screeningService.createScreening(TITLE, NAME, DATETIME);

        // Then
        verify(screeningRepository, times(1)).createScreening(MOVIE, ROOM, DATETIME);
    }

    @Test
    void testCreateScreeningShouldReturnExceptionWhenMovieNotFound() throws MovieNotFoundException {
        // Given
        doThrow(MovieNotFoundException.class)
                .when(movieRepository)
                .findMovieByTitle(anyString());

        // Then
        assertThrows(MovieNotFoundException.class, () -> {
            // When
            underTest.createScreening(TITLE, NAME, DATETIME);
        });
    }

    @Test
    void testCreateScreeningShouldReturnExceptionWhenRoomNotFound() throws RoomNotFoundException {
        // Given
        doThrow(RoomNotFoundException.class)
                .when(roomRepository)
                .findRoomByName(anyString());

        // Then
        assertThrows(RoomNotFoundException.class, () -> {
            // When
            underTest.createScreening(TITLE, NAME, DATETIME);
        });
    }

    @Test
    public void testDeleteScreeningReturnsExceptionWhenScreeningNotExists() throws ScreeningNotFoundException {
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

    @Test
    void testDeleteScreeningShouldReturnExceptionWhenScreeningNotExists() throws ScreeningNotFoundException {
        // Given
        doThrow(ScreeningNotFoundException.class)
                .when(screeningRepository)
                .deleteScreening(anyString(), anyString(), any());

        // Then
        assertThrows(ScreeningNotFoundException.class, () -> {
            // When
            underTest.deleteScreening(TITLE, NAME, DATETIME);
        });
    }

    @Test
    void testGetAllScreeningsShouldReturnsListOfExistingScreenings() {
        // Given
        given(screeningRepository.getAllScreening()).willReturn(SCREENINGS);

        // When
        List<Screening> current = underTest.getAllScreening();

        // Then
        assertThat(current, equalTo(SCREENINGS));
    }

    @Test
    void testDeleteScreeningShouldBeSuccessful() throws ScreeningNotFoundException {
        // When
       screeningService.deleteScreening(TITLE, NAME, DATETIME);

        // Then
        verify(screeningRepository, times(1)).deleteScreening(TITLE, NAME, DATETIME);
    }

    @Test
    void testIsStartInTheBreakPeriodShouldReturnTrue() {

    }

}