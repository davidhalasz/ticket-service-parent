package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.AdminDao;
import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.entity.RoomEntity;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.ServiceException.RoomAlreadyExistsException;
import com.epam.training.ticketservice.service.ServiceException.RoomNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaRoomRepository implements RoomRepository {

    private RoomDao roomDao;


    public JpaRoomRepository(RoomDao roomDao, AdminDao adminDao) {
        this.roomDao = roomDao;
    }

    @Override
    public void createRoom(RoomEntity room) throws RoomAlreadyExistsException {
        if (isRoomExists(room.getName())) {
            throw new RoomAlreadyExistsException("Room already exists");
        } else  {
            RoomEntity roomEntity = new RoomEntity();
            roomEntity.setName(room.getName());
            roomEntity.setRows(room.getRows());
            roomEntity.setColumns(room.getColumns());
            roomDao.save(roomEntity);
        }
    }

    @Override
    public RoomEntity updateRoom(String name, int rows, int columns) throws RoomNotFoundException {
        if (!isRoomExists(name)) {
            throw new RoomNotFoundException("Room not found");
        } else  {
            RoomEntity roomEntity = new RoomEntity();
            roomEntity.setName(name);
            roomEntity.setRows(rows);
            roomEntity.setColumns(columns);
            roomDao.save(roomEntity);
        }
        return null;
    }

    @Override
    public RoomEntity deleteRoom(String name) throws RoomNotFoundException {
        if (!isRoomExists(name)) {
            throw new RoomNotFoundException("Room not found");
        } else  {
            RoomEntity roomEntity = roomDao.findByName(name);
            roomDao.delete(roomEntity);
        }
        return null;
    }

    @Override
    public List<RoomEntity> getAllRoom() {
        List<RoomEntity> roomEntities = roomDao.findAll();
        return roomEntities;
    }


    private boolean isRoomExists(String name) {
        Optional<RoomEntity> roomEntity = Optional.ofNullable(roomDao.findByName(name));
        return roomEntity.isPresent();
    }
}