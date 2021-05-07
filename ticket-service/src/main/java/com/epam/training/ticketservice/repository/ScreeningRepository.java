package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.exceptions.ScreeningNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningRepository {

    List<Screening> getAllScreenings();

    void createScreening(Movie movie, Room room, LocalDateTime startDateTime);

    void deleteScreening(String movieTitle, String roomName, LocalDateTime startDateTime)
            throws ScreeningNotFoundException;
}
