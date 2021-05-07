package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.exceptions.InvalidRuntimeException;
import com.epam.training.ticketservice.exceptions.MovieAlreadyExistsException;
import com.epam.training.ticketservice.exceptions.MovieNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class JpaMovieRepositoryTest {

    @InjectMocks
    JpaMovieRepository movieRepository;

    @Mock
    MovieDao movieDao;

    private final static String TITLE = "title";
    private final static String GENRE = "genre";
    private final static int RUNTIME = 10;
    private static final Movie movie = new Movie(TITLE, GENRE, RUNTIME);
    private static final MovieEntity movieEntity = new MovieEntity(TITLE, GENRE, RUNTIME);

    @Test
    void testFindMovieShouldReturnExceptionWhenMovieIsFound()
            throws MovieNotFoundException, InvalidRuntimeException {

        // When
        assertThrows(MovieNotFoundException.class, () -> {
            // Then
            movieRepository.getMovieByTitle(anyString());
        });
    }

    @Test
    void testCreateMovieShouldReturnExceptionWhenMovieIsAlreadyExists() {
        // Given
        when(movieDao.findById(anyString())).thenReturn(Optional.of(movieEntity));

        // then
        assertThrows(MovieAlreadyExistsException.class, () -> {
           //  When
            movieRepository.createMovie(movie);
        });
    }

}