package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.exceptions.RoomNotFoundException;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

    private static final Room room1 = new Room(NAME, ROWS, COLUMNS);
    private static final List<Room> rooms = List.of(room1, room1);
    private static final RoomEntity roomEnity = new RoomEntity(NAME, ROWS, COLUMNS);
    private static final List<RoomEntity> roomEntities = List.of(roomEnity, roomEnity);


    @Test
    void testGetAllRoomsShouldReturnListOfAllRooms() {
        given(roomDao.findAll()).willReturn(roomEntities);
        given(mapper.mapRoomEntities(roomEntities)).willReturn(rooms);

        List<Room> actualResult = roomRepository.getAllRooms();

        assertThat(actualResult, equalTo(rooms));
    }

    @Test
    void testIsRoomExistsShouldReturnTrueWhenRoomIsExists() {
        // Given
        when(roomDao.findByName(NAME)).thenReturn(roomEnity);

        boolean actualResult = roomRepository.isRoomExists(NAME);

        assertTrue(actualResult);
    }

    @Test
    void testGetRoomByNameShouldReturnRoomWhenExists() throws RoomNotFoundException {
        when(roomDao.findByName(NAME)).thenReturn(roomEnity);
        when(mapper.mapRoomEntity(roomEnity)).thenReturn(room1);

        Room actualResult = roomRepository.getRoomByName(NAME);

        assertThat(actualResult, equalTo(room1));
    }

    @Test
    void testDeleteRoomShouldBeSuccess() throws RoomNotFoundException {
        when(roomDao.findByName(NAME)).thenReturn(roomEnity);

        roomRepository.deleteRoom(NAME);

        verify(roomDao, times(1)).delete(roomEnity);
    }

}