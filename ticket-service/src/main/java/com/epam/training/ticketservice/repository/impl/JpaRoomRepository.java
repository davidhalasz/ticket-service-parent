package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.AdminDao;
import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.dataaccess.entity.RoomEntity;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.MapperRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.ServiceException.RoomAlreadyExistsException;
import com.epam.training.ticketservice.service.ServiceException.RoomNotFoundException;
import lombok.RequiredArgsConstructor;
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
    public void createRoom(Room room) throws RoomAlreadyExistsException {
        if (isRoomExists(room.getName())) {
            throw new RoomAlreadyExistsException("Room already exists");
        } else  {
            roomDao.save(mapperRepository.mapperRoom(room));
        }
    }

    @Override
    public Room updateRoom(String name, int rows, int columns) throws RoomNotFoundException {
        if (!isRoomExists(name)) {
            throw new RoomNotFoundException("Room not found");
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
    public Room FindRoomByName(String name) {
        if (isRoomExists(name)) {
            return mapRoomEntity(roomDao.findByName(name));
        } else {
            return null;
        }
    }

    private List<Room> mapRoomEntities(List<RoomEntity> roomEntities) {
        return roomEntities.stream()
                .map(this::mapRoomEntity)
                .collect(Collectors.toList());
    }

    private Room mapRoomEntity(RoomEntity roomEntity) {
        return new Room(roomEntity.getName(), roomEntity.getRows(), roomEntity.getColumns());
    }


    private boolean isRoomExists(String name) {
        Optional<RoomEntity> roomEntity = Optional.ofNullable(roomDao.findByName(name));
        return roomEntity.isPresent();
    }
}
