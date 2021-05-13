package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.entity.RoomEntity;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.mapper.MapperInterface;
import com.epam.training.ticketservice.exceptions.InvalidRoomParameterException;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.exceptions.RoomAlreadyExistsException;
import com.epam.training.ticketservice.exceptions.RoomNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class JpaRoomRepository implements RoomRepository {

    private final RoomDao roomDao;
    private final MapperInterface mapper;


    @Override
    public void createRoom(Room room)
            throws RoomAlreadyExistsException, InvalidRoomParameterException {

        if (isRoomExists(room.getName())) {
            throw new RoomAlreadyExistsException("Room already exists");
        } else if (room.getRows() < 1 || room.getColumns() < 1) {
            throw new InvalidRoomParameterException("Room's rows and columns cannot be null");
        } else {
            RoomEntity roomEntity = mapper.mapperRoom(room);
            roomDao.save(roomEntity);
        }
    }

    @Override
    public Room updateRoom(String name, int rows, int columns)
            throws RoomNotFoundException, InvalidRoomParameterException {

        if (!isRoomExists(name)) {
            throw new RoomNotFoundException("Room not found");
        } else if (rows < 1 || columns < 1) {
            throw new InvalidRoomParameterException("Room's rows and columns cannot be null");
        } else  {
            RoomEntity roomEntity = roomDao.findByName(name);
            roomEntity.setRows(rows);
            roomEntity.setColumns(columns);
            roomDao.save(roomEntity);
        }
        return null;
    }

    @Override
    public Room deleteRoom(String name) throws RoomNotFoundException {
        if (!isRoomExists(name)) {
            throw new RoomNotFoundException("Room not found");
        } else  {
            RoomEntity roomEntity = roomDao.findByName(name);
            roomDao.delete(roomEntity);
        }
        return null;
    }

    @Override
    public List<Room> getAllRooms() {
        List<RoomEntity> roomEntities = roomDao.findAll();
        return mapper.mapRoomEntities(roomEntities);
    }

    @Override
    public Room getRoomByName(String name) throws RoomNotFoundException {
        if (isRoomExists(name)) {
            return mapper.mapRoomEntity(roomDao.findByName(name));
        } else {
            throw new RoomNotFoundException("There is no such room");
        }
    }

    public boolean isRoomExists(String name) {
        Optional<RoomEntity> roomEntity = Optional.ofNullable(roomDao.findByName(name));
        return roomEntity.isPresent();
    }
}
