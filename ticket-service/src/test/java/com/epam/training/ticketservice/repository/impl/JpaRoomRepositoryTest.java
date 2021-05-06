package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.entity.RoomEntity;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.MapperRepository;
import com.epam.training.ticketservice.repository.RepositoryException.InvalidRoomParameterException;
import com.epam.training.ticketservice.repository.RepositoryException.RoomAlreadyExistsException;
import com.epam.training.ticketservice.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaRoomRepositoryTest {

    private JpaRoomRepository underTest;

    @Mock
    RoomDao roomDao;

    @Mock
    MapperRepository mapper;

    @Spy
    @InjectMocks
    JpaRoomRepository roomRepository;

    private static final String NAME = "name";
    private static final int ROWS = 10;
    private static final int COLUMNS = 10;

    private Room room;
    private RoomEntity roomEntity;
    private List<Room> rooms;
    private List<RoomEntity> roomEntities;

    private static Room createRoom(String roomName, int rows, int columns) {
        Room result = null;
        result = new Room(roomName, rows, columns);
        return result;
    }

}