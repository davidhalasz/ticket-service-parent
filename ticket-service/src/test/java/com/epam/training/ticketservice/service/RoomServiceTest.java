package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.RepositoryException.InvalidRoomParameterException;
import com.epam.training.ticketservice.repository.RepositoryException.RoomAlreadyExistsException;
import com.epam.training.ticketservice.repository.RepositoryException.RoomNotFoundException;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

class RoomServiceTest {

    private static final String NAME = "room 1";
    private static final String NAME2 = "room 2";
    private static final int ROWS = 10;
    private static final int COLUMNS = 10;
    private static final Room ROOM = new Room(NAME, ROWS, COLUMNS);
    private static final Room ROOM2 = new Room(NAME2, ROWS, COLUMNS);
    private static final List<Room> ROOMS = List.of(ROOM, ROOM2);

    private RoomService underTest;
    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new RoomService(roomRepository);
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
        given(underTest.getAllRoom()).willReturn(ROOMS);

        // When
        List<Room> current = roomRepository.getAllRoom();

        // Then
        assertThat(current, equalTo(ROOMS));
    }

    @Test
    void testFindRoomByNameShouldReturnExceptionWhenRoomNotFound() throws RoomNotFoundException {
        // Given
        given(roomRepository.findRoomByName(anyString()))
                .willThrow(RoomNotFoundException.class);

        // Then
        assertThrows(RoomNotFoundException.class, () -> {
            // When
            roomRepository.findRoomByName(NAME);
        });
    }


    @Test
    void testFindRoomByNameShouldReturnFoundRoomWhenExistingRoom() throws RoomNotFoundException {
        // Given
        given(roomRepository.findRoomByName(NAME)).willReturn(ROOM);

        // When
        Room current = roomRepository.findRoomByName(NAME);

        // Then
        assertThat(current, equalTo(ROOM));
    }


}