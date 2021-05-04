package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.service.ServiceException.OverlappingException;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningRepository {

    List<Screening> getAllScreening();

    void createScreening(Movie movie, Room room, LocalDateTime startDateTime);
}
