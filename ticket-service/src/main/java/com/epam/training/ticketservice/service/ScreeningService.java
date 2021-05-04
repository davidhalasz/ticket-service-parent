package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.MapperRepository;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.service.ServiceException.OverlappingException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScreeningService {

    private ScreeningRepository screeningRepository;
    private MovieRepository movieRepository;
    private RoomRepository roomRepository;

    public ScreeningService(ScreeningRepository screeningRepository,
                            MovieRepository movieRepository,
                            RoomRepository roomRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    public void createScreening(String movieTitle, String roomName, LocalDateTime startDateTime) throws OverlappingException {
        Movie movie = movieRepository.findMovieByTitle(movieTitle);
        Room room = roomRepository.FindRoomByName(roomName);
        LocalDateTime endDateTime = startDateTime.plusMinutes(movie.getRuntime());

        if (isThereOverlappingScreening(startDateTime, endDateTime, room.getName())) {
            throw new OverlappingException("There is an overlapping screening");
        } else if (isStartInTheBreakPeriod(startDateTime, endDateTime, room.getName())) {
            throw new OverlappingException("This would start in the break period after another screening in this room");
        }
        screeningRepository.createScreening(movie, room, startDateTime);
    }


    private boolean isThereOverlappingScreening(LocalDateTime startDate,
                                                LocalDateTime endDate,
                                                String roomName) {
        return screeningRepository.getAllScreening().stream()
                .filter(screening -> screening.getRoom().getName().equals(roomName))
                .anyMatch(screening -> {
                    LocalDateTime screeningStart = screening.getStartDate();
                    LocalDateTime screeningEnd = screening.getStartDate()
                            .plusMinutes(screening.getMovie().getRuntime());
                    return isWithinRange(screeningStart, screeningEnd, startDate)
                            || isWithinRange(screeningStart, screeningEnd, endDate);

                });
    }

    private boolean isStartInTheBreakPeriod(LocalDateTime startDate,
                                            LocalDateTime endDate,
                                            String roomName) {
        return screeningRepository.getAllScreening().stream()
                .filter(screening -> screening.getRoom().getName().equals(roomName))
                .anyMatch(screening -> {
                    LocalDateTime screeningStart = screening.getStartDate();
                    LocalDateTime screeningEnd = screening.getStartDate().plusMinutes(10)
                            .plusMinutes(screening.getMovie().getRuntime());
                    return isWithinRange(screeningStart, screeningEnd, startDate)
                            || isWithinRange(screeningStart, screeningEnd, endDate);

                });
    }

    private boolean isWithinRange(LocalDateTime startDate, LocalDateTime endDate, LocalDateTime checkStartDateTime) {
        return (checkStartDateTime.isEqual(startDate) || checkStartDateTime.isEqual(endDate))
                || (checkStartDateTime.isBefore(endDate) && checkStartDateTime.isAfter(startDate));
    }

}
