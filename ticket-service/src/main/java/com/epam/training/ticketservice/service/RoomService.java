package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.exceptions.InvalidRoomParameterException;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.exceptions.RoomAlreadyExistsException;
import com.epam.training.ticketservice.exceptions.RoomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void createRoom(String name, int rows, int columns)
            throws RoomAlreadyExistsException, InvalidRoomParameterException {

        Room room = new Room(name, rows, columns);
        roomRepository.createRoom(room);
    }

    public void updateRoom(String name, int rows, int columns)
            throws RoomNotFoundException, InvalidRoomParameterException {

        roomRepository.updateRoom(name, rows, columns);
    }

    public Room deleteRoom(String name) throws RoomNotFoundException {
        return roomRepository.deleteRoom(name);
    }

    public List<Room> getAllRooms() {
        return roomRepository.getAllRooms();
    }
}
