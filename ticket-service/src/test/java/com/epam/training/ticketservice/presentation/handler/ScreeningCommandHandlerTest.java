package com.epam.training.ticketservice.presentation.handler;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RepositoryException.*;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.service.AdminService;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.ScreeningService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.print.attribute.standard.MediaSize;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class ScreeningCommandHandlerTest {
    @InjectMocks
    private ScreeningCommandHandler screeningCommandHandler;
    @Mock
    private ScreeningService screeningService;
    @Mock
    private AdminService adminService;


    private final static String UNPRIVILIGED_MSG = "You are not signed in";
    private final static String SCREENING_ADDED = "Screening added";
    private final static String SCREENING_UPDATED = "Screening updated";
    private final static String SCREENING_DELETED = "Screening deleted";
    private final static String INVALID_RUNTIME_MSG = "Runtime cannot be null";
    private final static String SCREENING_NOT_FOUND = "There is no such screening";
    private final static String TITLE = "movie title";
    private final static String NAME = "room name";
    private final static String DATETIME = "2021-05-06 10:00";
    private final static String SCREENING_EXISTS_MSG = "Screening already exists";
    private final static String EMPTY_LIST_MSG = "There are no screenings";
    private final static String OVERLAPPING_MSG ="There is an overlapping screening";
    private final static String OL_IN_BREAK_MSG = "This would start in the break period after another screening in this room";
    private final static String ROOM_NOT_FOUND = "Room not found";
    private final static String MOVIE_NOT_FOUND = "Movie not found";

    private static final OverlappingException OVERLAPPING_EXCEPTION
            = new OverlappingException(OVERLAPPING_MSG);
    private static final OverlappingInBreakException OVERLAPPING_IN_BREAK_EXCEPTION
            = new OverlappingInBreakException(OL_IN_BREAK_MSG);
    private static final RoomNotFoundException ROOM_NOT_FOUND_EXCEPTION
            = new RoomNotFoundException(ROOM_NOT_FOUND);
    private static final MovieNotFoundException MOVIE_NOT_FOUND_EXCEPTION
            = new MovieNotFoundException(MOVIE_NOT_FOUND);
    private static final ScreeningNotFoundException SCREENING_NOT_FOUND_EXCEPTION
            = new ScreeningNotFoundException(SCREENING_NOT_FOUND);

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final LocalDateTime FORMATTED_DATETIME = LocalDateTime.parse(DATETIME, dateTimeFormatter);


    @Test
    void testCreateScreeningWhenAdminIsSignedIn()
            throws OverlappingException,
            OverlappingInBreakException,
            RoomNotFoundException,
            MovieNotFoundException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);

        // When
        String current = screeningCommandHandler.createScreening(TITLE, NAME, DATETIME);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(screeningService, times(1)).createScreening(TITLE, NAME, FORMATTED_DATETIME);
        assertThat(current, equalTo(SCREENING_ADDED));
    }

    @Test
    void testCreateScreeningReturnErrorWhenAdminIsNotSignedIn()
            throws OverlappingException, OverlappingInBreakException, RoomNotFoundException, MovieNotFoundException {
        // Given
        given(adminService.loggedAdmin()).willReturn(false);

        // When
        String current = screeningCommandHandler.createScreening(TITLE, NAME, DATETIME);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        assertThat(current, equalTo(UNPRIVILIGED_MSG));
    }

    @Test
    void testCreateScreeningShouldReturnExceptionWhenScreeningIsOverlapping()
            throws OverlappingException,
            OverlappingInBreakException,
            MovieNotFoundException,
            RoomNotFoundException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);
        doThrow(OVERLAPPING_EXCEPTION)
                .when(screeningService)
                .createScreening(anyString(), anyString(), any());

        // When
        String current = screeningCommandHandler.createScreening(TITLE, NAME, DATETIME);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(screeningService, times(1)).createScreening(TITLE, NAME, FORMATTED_DATETIME);
        assertThat(current, equalTo(OVERLAPPING_MSG));
    }

    @Test
    void testCreateScreeningShouldReturnExceptionWhenScreeningIsOverlappingInBreak()
            throws OverlappingException,
            OverlappingInBreakException,
            MovieNotFoundException,
            RoomNotFoundException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);
        doThrow(OVERLAPPING_IN_BREAK_EXCEPTION)
                .when(screeningService)
                .createScreening(anyString(), anyString(), any());

        // When
        String current = screeningCommandHandler.createScreening(TITLE, NAME, DATETIME);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(screeningService, times(1)).createScreening(TITLE, NAME, FORMATTED_DATETIME);
        assertThat(current, equalTo(OL_IN_BREAK_MSG));
    }

    @Test
    void testCreateScreeningShouldReturnExceptionWhenMovieNotFound()
            throws OverlappingException,
            OverlappingInBreakException,
            MovieNotFoundException,
            RoomNotFoundException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);
        doThrow(MOVIE_NOT_FOUND_EXCEPTION)
                .when(screeningService)
                .createScreening(anyString(), anyString(), any());

        // When
        String current = screeningCommandHandler.createScreening(TITLE, NAME, DATETIME);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(screeningService, times(1)).createScreening(TITLE, NAME, FORMATTED_DATETIME);
        assertThat(current, equalTo(MOVIE_NOT_FOUND));
    }

    @Test
    void testCreateScreeningShouldReturnExceptionWhenRoomNotFound()
            throws OverlappingException,
            OverlappingInBreakException,
            MovieNotFoundException,
            RoomNotFoundException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);
        doThrow(ROOM_NOT_FOUND_EXCEPTION)
                .when(screeningService)
                .createScreening(anyString(), anyString(), any());

        // When
        String current = screeningCommandHandler.createScreening(TITLE, NAME, DATETIME);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(screeningService, times(1)).createScreening(TITLE, NAME, FORMATTED_DATETIME);
        assertThat(current, equalTo(ROOM_NOT_FOUND));
    }

    @Test
    void testDeleteScreeningShouldReturnDeletedScreening()
            throws ScreeningNotFoundException {
        // Given
        given(adminService.loggedAdmin()).willReturn(true);

        // When
        String current = screeningCommandHandler.deleteScreening(TITLE, NAME, DATETIME);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        assertThat(current, equalTo(SCREENING_DELETED));
    }

    @Test
    void testDeleteScreeningShouldReturnUnsignedMessageWhenAdminIsNotLoggedIn()
            throws ScreeningNotFoundException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(false);

        // When
        String current = screeningCommandHandler.deleteScreening(TITLE, NAME, DATETIME);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        assertThat(current, equalTo(UNPRIVILIGED_MSG));
    }

    @Test
    void testListScreeningShouldReturnEmptyScreeningMessageWhenScreeningsEmpty() {
        // Given
        given(screeningService.getAllScreening()).willReturn(List.of());

        // When
        String current = screeningCommandHandler.listScreenings();

        // Then
        verify(screeningService, times(1)).getAllScreening();
        assertThat(current, equalTo(EMPTY_LIST_MSG));
    }

}