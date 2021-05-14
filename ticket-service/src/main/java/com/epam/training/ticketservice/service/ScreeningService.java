package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.exceptions.OverlappingException;
import com.epam.training.ticketservice.exceptions.OverlappingInBreakException;
import com.epam.training.ticketservice.exceptions.MovieNotFoundException;
import com.epam.training.ticketservice.exceptions.RoomNotFoundException;
import com.epam.training.ticketservice.exceptions.ScreeningNotFoundException;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    public void createScreening(String movieTitle, String roomName, LocalDateTime startDateTime)
            throws OverlappingException, OverlappingInBreakException,
            RoomNotFoundException, MovieNotFoundException {

        Movie movie = movieRepository.getMovieByTitle(movieTitle);
        Room room = roomRepository.getRoomByName(roomName);
        LocalDateTime endDateTime = startDateTime.plusMinutes(movie.getRuntime());

        if (isThereOverlappingScreening(startDateTime, endDateTime, room.getName())) {
            throw new OverlappingException("There is an overlapping screening");
        } else if (isStartInTheBreakPeriod(startDateTime, endDateTime, room.getName())) {
            throw new OverlappingInBreakException(
                    "This would start in the break period after another screening in this room"
            );
        }
        screeningRepository.createScreening(movie, room, startDateTime);
    }

    public boolean isThereOverlappingScreening(LocalDateTime startDate,
                                                LocalDateTime endDate,
                                                String roomName) {

        return screeningRepository.getAllScreenings().stream()
                .filter(screening -> screening.getRoom().getName().equals(roomName))
                .anyMatch(screening -> {
                    LocalDateTime screeningStart = screening.getStartDate();
                    LocalDateTime screeningEnd = screening.getStartDate()
                            .plusMinutes(screening.getMovie().getRuntime());
                    return isWithinRange(screeningStart, screeningEnd, startDate)
                            || isWithinRange(screeningStart, screeningEnd, endDate);

                });
    }

    public boolean isStartInTheBreakPeriod(LocalDateTime startDateTime,
                                            LocalDateTime endDateTime,
                                            String roomName) {

        return screeningRepository.getAllScreenings().stream()
                .filter(screening -> screening.getRoom().getName().equals(roomName))
                .anyMatch(screening -> {
                    LocalDateTime screeningStart = screening.getStartDate().plusMinutes(10);
                    LocalDateTime screeningEnd = screening.getStartDate()
                            .plusMinutes((screening.getMovie().getRuntime())).plusMinutes(10);
                    return checkBreakTime(screeningStart, screeningEnd, startDateTime)
                            || checkBreakTime(screeningStart, screeningEnd, endDateTime);
                });
    }

    public boolean isWithinRange(LocalDateTime startDate, LocalDateTime endDate, LocalDateTime checkStartDateTime) {

        return (checkStartDateTime.isEqual(startDate) || checkStartDateTime.isEqual(endDate))
                || (checkStartDateTime.isBefore(endDate) && checkStartDateTime.isAfter(startDate));
    }

    private boolean checkBreakTime(LocalDateTime startDate, LocalDateTime endDate,
                                            LocalDateTime checkDateTime) {
        return checkDateTime.isAfter(startDate) && checkDateTime.isBefore(endDate);
    }

    public void deleteScreening(String movieTitle, String roomName, LocalDateTime startDateTime)
            throws ScreeningNotFoundException {

        screeningRepository.deleteScreening(movieTitle, roomName, startDateTime);
    }

    public List<Screening> getAllScreenings() {
        return screeningRepository.getAllScreenings();
    }


}
