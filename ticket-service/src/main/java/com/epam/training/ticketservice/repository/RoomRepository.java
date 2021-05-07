package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.exceptions.InvalidRoomParameterException;
import com.epam.training.ticketservice.exceptions.RoomAlreadyExistsException;
import com.epam.training.ticketservice.exceptions.RoomNotFoundException;

import java.util.List;


public interface RoomRepository {
    void createRoom(Room room) throws RoomAlreadyExistsException, InvalidRoomParameterException;

    Room updateRoom(String name, int rows, int columns)
            throws RoomNotFoundException, InvalidRoomParameterException;

    Room deleteRoom(String name) throws RoomNotFoundException;

    List<Room> getAllRooms();

    Room getRoomByName(String name) throws RoomNotFoundException;
}
