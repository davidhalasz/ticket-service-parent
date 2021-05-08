package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.exceptions.DeleteException;
import com.epam.training.ticketservice.exceptions.InvalidRoomParameterException;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.exceptions.RoomAlreadyExistsException;
import com.epam.training.ticketservice.exceptions.RoomNotFoundException;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;

    public RoomService(RoomRepository roomRepository, ScreeningRepository screeningRepository) {
        this.roomRepository = roomRepository;
        this.screeningRepository = screeningRepository;
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

    public Room deleteRoom(String name) throws RoomNotFoundException, DeleteException {
        List<Screening> screenings = screeningRepository.getAllScreenings();
        Screening screening = screenings.stream()
                .filter(currentScreening -> currentScreening.getRoom().getName().equals(name))
                .findFirst()
                .orElse(null);
        if(screening != null) {
            throw new DeleteException("You cannot delete this room because there is a screening in this room.");
        }
        return roomRepository.deleteRoom(name);
    }
    public List<Room> getAllRooms() {
        return roomRepository.getAllRooms();
    }
}
