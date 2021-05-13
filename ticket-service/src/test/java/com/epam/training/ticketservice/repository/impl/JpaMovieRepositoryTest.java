package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.exceptions.AdminIsNotLoggedInException;
import com.epam.training.ticketservice.exceptions.InvalidRuntimeException;
import com.epam.training.ticketservice.exceptions.MovieAlreadyExistsException;
import com.epam.training.ticketservice.exceptions.MovieNotFoundException;
import com.epam.training.ticketservice.repository.mapper.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.annotation.Testable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class JpaMovieRepositoryTest {

    @InjectMocks
    JpaMovieRepository movieRepository;

    @Mock
    MovieDao movieDao;

    @Mock
    Mapper mapper;

    private final static String TITLE = "title";
    private final static String GENRE = "genre";
    private final static int RUNTIME = 10;
    private final static int INVALIDRUNTIME = 0;
    private static final Movie movie = new Movie(TITLE, GENRE, RUNTIME);
    private static final MovieEntity movieEntity = new MovieEntity(TITLE, GENRE, RUNTIME);

    @Test
    void testCreateMovieShouldBeSuccess() throws InvalidRuntimeException, MovieAlreadyExistsException {
        // Given
        given(mapper.mapperMovie(movie)).willReturn(movieEntity);

        // When
        movieRepository.createMovie(movie);

        // Then
        verify(movieDao, times(1)).save(movieEntity);
    }

    @Test
    void testMovieUpdateShouldBeSuccess() throws InvalidRuntimeException, MovieNotFoundException {
        // Given
        given(movieDao.findById(TITLE)).willReturn(Optional.of(movieEntity));
        given(movieDao.findMovieByTitle(TITLE)).willReturn(movieEntity);

        // When
        movieRepository.updateMovie(TITLE, GENRE, RUNTIME);

        // Then
        verify(movieDao, times(1)).save(movieEntity);
    }

    @Test
    void testMovieShouldReturnExceptionWhenMovieNotFound() {
        // Given
        given(movieDao.findById(anyString())).willReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> {
            movieRepository.updateMovie(TITLE, GENRE, RUNTIME);
        });
    }

    @Test
    void testMovieShouldReturnExceptionWhenRuntimeIsInvalid() {
        Exception exception = null;

        // When
        try {
            movieRepository.updateMovie(TITLE, GENRE, INVALIDRUNTIME);
        } catch (InvalidRuntimeException | MovieNotFoundException e) {
             exception = e;
        }

        // Then
        assertNotNull(exception);
    }

    @Test
    void testDeleteMovieShouldBeSuccess() throws MovieNotFoundException {
        // Given
        given(movieDao.findById(TITLE)).willReturn(Optional.of(movieEntity));
        given(movieDao.findMovieByTitle(TITLE)).willReturn(movieEntity);

        // When
        movieRepository.deleteMovie(TITLE);

        // Then
        verify(movieDao, times(1)).delete(movieEntity);
    }

    @Test
    void testDeleteMovieShouldReturnExceptionWhenMovieNotFound() {
        // Given
        given(movieDao.findById(anyString())).willReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> {
            movieRepository.deleteMovie(TITLE);
        });
    }

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