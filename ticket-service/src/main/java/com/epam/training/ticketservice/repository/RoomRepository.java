package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.RepositoryException.RoomAlreadyExistsException;
import com.epam.training.ticketservice.repository.RepositoryException.RoomNotFoundException;

import java.util.List;


public interface RoomRepository {
    void createRoom(Room room) throws RoomAlreadyExistsException;

    Room updateRoom(String name, int rows, int columns) throws RoomNotFoundException;

    Room deleteRoom(String name) throws RoomNotFoundException;

    List<Room> getAllRoom();

    Room findRoomByName(String name) throws RoomNotFoundException;
}
