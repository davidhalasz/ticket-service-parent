package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dataaccess.entity.RoomEntity;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.ServiceException.RoomAlreadyExistsException;
import com.epam.training.ticketservice.service.ServiceException.RoomNotFoundException;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Collection;
import java.util.List;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room createRoom(String name, int rows, int columns) throws RoomAlreadyExistsException {
        Room room = new Room(name, rows, columns);
        roomRepository.createRoom(room);
        return room;
    }

    public Room updateRoom(String name, int rows, int columns) throws RoomNotFoundException {
        return roomRepository.updateRoom(name, rows, columns);
    }

    public Room deleteRoom(String name) throws RoomNotFoundException {
        return roomRepository.deleteRoom(name);
    }

    public List<Room> getAllRoom() {
        return roomRepository.getAllRoom();
    }
}
