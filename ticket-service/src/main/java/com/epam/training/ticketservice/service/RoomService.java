package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dataaccess.entity.RoomEntity;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.ServiceException.RoomAlreadyExistsException;
import com.epam.training.ticketservice.service.ServiceException.RoomNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public RoomEntity createRoom(String name, int rows, int columns) throws RoomAlreadyExistsException {
        RoomEntity room = new RoomEntity(name, rows, columns);
        roomRepository.createRoom(room);
        return room;
    }

    public RoomEntity updateRoom(String name, int rows, int columns) throws RoomNotFoundException {
        return roomRepository.updateRoom(name, rows, columns);
    }
}
