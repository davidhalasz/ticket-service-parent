package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.service.ServiceException.RoomAlreadyExistsException;
import com.epam.training.ticketservice.service.ServiceException.RoomNotFoundException;

import java.util.List;


public interface RoomRepository {
    void createRoom(Room room) throws RoomAlreadyExistsException;

    Room updateRoom(String name, int rows, int columns) throws RoomNotFoundException;

    Room deleteRoom(String name) throws RoomNotFoundException;

    List<Room> getAllRoom();

    Room FindRoomByName(String name);
}
