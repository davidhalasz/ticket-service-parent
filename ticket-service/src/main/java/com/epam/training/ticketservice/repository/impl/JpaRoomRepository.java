package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.entity.RoomEntity;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.MapperRepository;
import com.epam.training.ticketservice.repository.RepositoryException.InvalidRoomParameterException;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.RepositoryException.RoomAlreadyExistsException;
import com.epam.training.ticketservice.repository.RepositoryException.RoomNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaRoomRepository implements RoomRepository {

    private final RoomDao roomDao;
    private final MapperRepository mapperRepository;


    public JpaRoomRepository(RoomDao roomDao, MapperRepository mapperRepository) {
        this.roomDao = roomDao;
        this.mapperRepository = mapperRepository;
    }

    @Override
    public void createRoom(Room room) throws RoomAlreadyExistsException, InvalidRoomParameterException {
        if (isRoomExists(room.getName())) {
            throw new RoomAlreadyExistsException("Room already exists");
        } else if (room.getRows() < 1 || room.getColumns() < 1) {
            throw new InvalidRoomParameterException("Room's rows and columns cannot be null");
        } else {
            roomDao.save(mapperRepository.mapperRoom(room));
        }
    }

    @Override
    public Room updateRoom(String name, int rows, int columns) throws RoomNotFoundException, InvalidRoomParameterException {
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
    public List<Room> getAllRoom() {
        List<RoomEntity> roomEntities = roomDao.findAll();
        return mapRoomEntities(roomEntities);
    }

    @Override
    public Room findRoomByName(String name) throws RoomNotFoundException{
        if (isRoomExists(name)) {
            return mapRoomEntity(roomDao.findByName(name));
        } else {
            throw new RoomNotFoundException("There is no such room");
        }
    }

    private List<Room> mapRoomEntities(List<RoomEntity> roomEntities) {
        return roomEntities.stream()
                .map(this::mapRoomEntity)
                .collect(Collectors.toList());
    }

    public Room mapRoomEntity(RoomEntity roomEntity) {
        return new Room(roomEntity.getName(), roomEntity.getRows(), roomEntity.getColumns());
    }

    public boolean isRoomExists(String name) {
        Optional<RoomEntity> roomEntity = Optional.ofNullable(roomDao.findByName(name));
        return roomEntity.isPresent();
    }
}
