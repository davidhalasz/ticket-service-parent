package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.exceptions.DeleteException;
import com.epam.training.ticketservice.exceptions.InvalidRoomParameterException;
import com.epam.training.ticketservice.exceptions.RoomAlreadyExistsException;
import com.epam.training.ticketservice.exceptions.RoomNotFoundException;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class RoomServiceTest {

    private static final String NAME = "room 1";
    private static final String NAME2 = "room 2";
    private static final int ROWS = 10;
    private static final int COLUMNS = 10;
    private static final Room ROOM = new Room(NAME, ROWS, COLUMNS);
    private static final Room ROOM2 = new Room(NAME2, ROWS, COLUMNS);
    private static final List<Room> ROOMS = List.of(ROOM, ROOM2);

    private static final String TITLE = "title";
    private static final String GENRE = "genre";
    private static final int RUNTIME = 10;
    private static final Movie MOVIE = new Movie(TITLE, GENRE, RUNTIME);

    private static final String STR_DATETIME = "2021-05-06 10:00";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final LocalDateTime DATETIME = LocalDateTime.parse(STR_DATETIME, dateTimeFormatter);

    private static final Screening SCREENING = new Screening(MOVIE, ROOM, DATETIME);
    private static final List<Screening> SCREENINGS = List.of(SCREENING, SCREENING);

    private RoomService underTest;
    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ScreeningRepository screeningRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new RoomService(roomRepository, screeningRepository);
    }

    @Test
    void testCreateRoomShouldReturnExceptionWhenRoomExists()
            throws RoomAlreadyExistsException, InvalidRoomParameterException {
        // Given
        doThrow(RoomAlreadyExistsException.class)
                .when(roomRepository)
                .createRoom(any());

        // Then
        assertThrows(RoomAlreadyExistsException.class, () ->{
            // When
            roomService.createRoom(NAME, ROWS, COLUMNS);
        });
    }

    @Test
    void testUpdateRoomShouldReturnExceptionWhenRoomNotFound()
            throws RoomNotFoundException, InvalidRoomParameterException {
        // Given
        given(roomRepository.updateRoom(NAME, ROWS, COLUMNS))
                .willThrow(RoomNotFoundException.class);

        // Then
        assertThrows(RoomNotFoundException.class, () -> {
            // Then
            roomService.updateRoom(NAME, ROWS, COLUMNS);
        });
    }

    @Test
    void testDeleteRoomShouldReturnExceptionWhenRoomNotFound()
            throws RoomNotFoundException {
        // Given
        given(roomRepository.deleteRoom(anyString()))
                .willThrow(RoomNotFoundException.class);

        // Then
        assertThrows(RoomNotFoundException.class, () -> {
            // Then
            roomService.deleteRoom(NAME);
        });
    }

    @Test
    void testGetAllRoomShouldReturnListOfRooms() {
        // Given
        given(underTest.getAllRooms()).willReturn(ROOMS);

        // When
        List<Room> actualResult = roomRepository.getAllRooms();

        // Then
        assertThat(actualResult, equalTo(ROOMS));
    }

    @Test
    void testFindRoomByNameShouldReturnExceptionWhenRoomNotFound() throws RoomNotFoundException {
        // Given
        given(roomRepository.getRoomByName(anyString()))
                .willThrow(RoomNotFoundException.class);

        // Then
        assertThrows(RoomNotFoundException.class, () -> {
            // When
            roomRepository.getRoomByName(NAME);
        });
    }


    @Test
    void testFindRoomByNameShouldReturnFoundRoomWhenExistingRoom() throws RoomNotFoundException {
        // Given
        given(roomRepository.getRoomByName(NAME)).willReturn(ROOM);

        // When
        Room actualResult = roomRepository.getRoomByName(NAME);

        // Then
        assertThat(actualResult, equalTo(ROOM));
    }
/*
    @Test
    void testDeleteRoomShouldReturnExceptionWhenScreeningIsExistInThisRoom() {
        // Given
        given(screeningRepository.getAllScreenings()).willReturn(SCREENINGS);

        // Then
        assertThrows(DeleteException.class, () -> {
            // When
            roomService.deleteRoom(NAME);
        });
    }

    @Test
    void testDeleteRoomShouldBeSuccessWhenThereIsNoScreeningInThisRoom() throws DeleteException, RoomNotFoundException {
        // Given
        given(screeningRepository.getAllScreenings()).willReturn(SCREENINGS);

        // When
        roomService.deleteRoom(NAME2);

        // Then
        verify(roomRepository, times(1)).deleteRoom(NAME2);
    }
 */
}