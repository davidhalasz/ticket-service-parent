package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.dataaccess.entity.RoomEntity;
import com.epam.training.ticketservice.service.ServiceException.RoomAlreadyExistsException;
import com.epam.training.ticketservice.service.ServiceException.RoomNotFoundException;
import org.springframework.stereotype.Repository;


public interface RoomRepository {
    void createRoom(RoomEntity room) throws RoomAlreadyExistsException;

    RoomEntity updateRoom(String name, int rows, int columns) throws RoomNotFoundException;
}
