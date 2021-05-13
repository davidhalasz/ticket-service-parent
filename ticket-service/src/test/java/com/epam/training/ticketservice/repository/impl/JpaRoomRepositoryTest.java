package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.exceptions.*;
import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.entity.RoomEntity;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.mapper.MapperInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaRoomRepositoryTest {

    private JpaRoomRepository underTest;

    @Mock
    RoomDao roomDao;

    @Mock
    MapperInterface mapper;

    @Spy
    @InjectMocks
    JpaRoomRepository roomRepository;

    private static final String NAME = "name";
    private static final int ROWS = 10;
    private static final int COLUMNS = 10;
    private static final int INVALIDROWS = 0;
    private static final int INVALIDCOLUMNS = 0;
    private static final Room room1 = new Room(NAME, ROWS, COLUMNS);
    private static final List<Room> rooms = List.of(room1, room1);
    private static final RoomEntity roomEnity = new RoomEntity(NAME, ROWS, COLUMNS);
    private static final List<RoomEntity> roomEntities = List.of(roomEnity, roomEnity);
    private static final Room invalidRoom = new Room(NAME, INVALIDROWS, INVALIDCOLUMNS);

    @Test
    void testCreateRoomShouldBeSuccess() throws InvalidRoomParameterException, RoomAlreadyExistsException {
        // Given
        given(roomDao.findByName(NAME)).willReturn(null);
        given(mapper.mapperRoom(room1)).willReturn(roomEnity);

        // When
        roomRepository.createRoom(room1);

        // Then
        verify(roomDao, times(1)).save(roomEnity);
    }


    @Test
    void testCreateRoomShouldReturnExceptionWhenRoomIsExists() throws InvalidRoomParameterException, RoomAlreadyExistsException {
        Exception exception = null;
        // Given
        given(roomDao.findByName(NAME)).willReturn(roomEnity);

        // When
        try {
            roomRepository.createRoom(room1);
        } catch (RoomAlreadyExistsException e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
    }

    @Test
    void testCreateRoomShouldReturnInvalidException()  {
        Exception exception = null;
        // Given
        given(roomDao.findByName(NAME)).willReturn(null);

        // When
        try {
            roomRepository.createRoom(invalidRoom);
        } catch (InvalidRoomParameterException | RoomAlreadyExistsException e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
    }

    @Test
    void testUpdateRoomShouldReturnExceptionWhenRoomIsExists() throws InvalidRoomParameterException, RoomNotFoundException {
        Exception exception = null;
        // Given
        given(roomDao.findByName(NAME)).willReturn(null);

        // When
        try {
            roomRepository.updateRoom(NAME, ROWS, COLUMNS);
        } catch (RoomNotFoundException e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
    }

    @Test
    void testUpdateRoomShouldBeSuccess() throws RoomNotFoundException, InvalidRoomParameterException {
        // Given
        given(roomDao.findByName(NAME)).willReturn(roomEnity);

        // When
        roomRepository.updateRoom(NAME, ROWS, COLUMNS);

        // Then
        verify(roomDao, times(1)).save(roomEnity);

    }

    @Test
    void testUpdateRoomShouldReturnInvalidException() throws RoomNotFoundException {
        Exception exception = null;
        // Given
        given(roomDao.findByName(NAME)).willReturn(roomEnity);

        // When
        try {
            roomRepository.updateRoom(NAME, INVALIDROWS, INVALIDCOLUMNS);
        } catch (InvalidRoomParameterException e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
    }

    @Test
    void testGetAllRoomsShouldReturnListOfAllRooms() {
        // Given
        given(roomDao.findAll()).willReturn(roomEntities);
        given(mapper.mapRoomEntities(roomEntities)).willReturn(rooms);

        // When
        List<Room> actualResult = roomRepository.getAllRooms();

        // Then
        assertThat(actualResult, equalTo(rooms));
    }

    @Test
    void testIsRoomExistsShouldReturnTrueWhenRoomIsExists() {
        // Given
        when(roomDao.findByName(NAME)).thenReturn(roomEnity);

        // When
        boolean actualResult = roomRepository.isRoomExists(NAME);

        // Then
        assertTrue(actualResult);
    }

    @Test
    void testGetRoomByNameShouldReturnRoomWhenExists() throws RoomNotFoundException {
        // Given
        when(roomDao.findByName(NAME)).thenReturn(roomEnity);
        when(mapper.mapRoomEntity(roomEnity)).thenReturn(room1);

        // When
        Room actualResult = roomRepository.getRoomByName(NAME);

        // Then
        assertThat(actualResult, equalTo(room1));
    }

    @Test
    void testDeleteRoomShouldBeSuccess() throws RoomNotFoundException {
        // Given
        when(roomDao.findByName(NAME)).thenReturn(roomEnity);

        // When
        roomRepository.deleteRoom(NAME);

        // Then
        verify(roomDao, times(1)).delete(roomEnity);
    }

    @Test
    void testDeleteRoomShouldReturnRoomNotFoundException() throws RoomNotFoundException {
        // Given
        given(roomDao.findByName(NAME)).willReturn(null);

        // Then
        assertThrows(RoomNotFoundException.class, ()-> {
            // When
            roomRepository.deleteRoom(NAME);
        });

    }

    @Test
    void testGetRoomFindByNameShouldReturnException() throws RoomNotFoundException {
        // Given
        given(roomDao.findByName(NAME)).willReturn(null);

        // Then
        assertThrows(RoomNotFoundException.class, ()-> {
            // When
            roomRepository.getRoomByName(NAME);
        });
    }

}