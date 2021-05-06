package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RepositoryException.InvalidRuntimeException;
import com.epam.training.ticketservice.repository.RepositoryException.MovieAlreadyExistsException;
import com.epam.training.ticketservice.repository.RepositoryException.MovieNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    private MovieService underTest;
    private final static String TITLE1 = "Satantango";
    private final static String TITLE2 = "Cim 2";
    private final static String GENRE = "Drama";
    private static final int RUNTIME = 120;
    private static final int INVALID_RUNTIME = 0;


    private static final Movie MOVIE1 =  new Movie(TITLE1, GENRE, RUNTIME);
    private static final Movie MOVIE2 =  new Movie(TITLE2, GENRE, RUNTIME);
    private static final List<Movie> MOVIES = List.of(MOVIE1, MOVIE2);


    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new MovieService(movieRepository);
    }

    @Test
     void testGetAllMovieShouldReturnsListOfExistingMovies() {
        // Given
        given(movieRepository.getAllMovie()).willReturn(MOVIES);

        // When
        List<Movie> current = underTest.getAllMovie();

        // Then
        assertThat(current, equalTo(MOVIES));
    }

    @Test
     void testCreateMovieShouldReturnExceptionWhenTitleExists() throws MovieAlreadyExistsException, InvalidRuntimeException {
        // Given
        doThrow(MovieAlreadyExistsException.class)
                .when(movieRepository)
                .createMovie(any());

        // Then
        assertThrows(MovieAlreadyExistsException.class, () -> {
            // When
            movieService.addMovie(TITLE1, GENRE, RUNTIME);
        });
    }

    @Test
     void testCreateMovieShouldReturnExceptionWhenRuntimeIsInvalid() throws MovieAlreadyExistsException, InvalidRuntimeException{
        // Given
        doThrow(InvalidRuntimeException.class).when(movieRepository).createMovie(any());
        Exception exception = null;

        // When
        try {
            movieRepository.createMovie(underTest.addMovie(TITLE1, GENRE, INVALID_RUNTIME));
        } catch (InvalidRuntimeException e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);

    }

    @Test
     void testUpdateMovieWithExistingMovie() throws MovieNotFoundException, InvalidRuntimeException {

        // When
        movieService.updateMovie(TITLE1, GENRE, RUNTIME);

        // Then
        verify(movieRepository, times(1)).updateMovie(TITLE1, GENRE, RUNTIME);
    }

    @Test
     void testUpdateMovieWithNonExistingMovieShouldReturnException()
            throws MovieNotFoundException, InvalidRuntimeException {
        // Given
        doThrow(MovieNotFoundException.class).when(movieRepository).updateMovie(anyString(), anyString(), anyInt());
        Exception exception = null;

        // When
        try {
            movieService.updateMovie(TITLE1, GENRE, RUNTIME);
        } catch (MovieNotFoundException e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
    }

    @Test
        void testUpdateMovieShouldReturnExceptionWhenUpdateWithInvalidRuntime()
            throws MovieNotFoundException, InvalidRuntimeException {
        // Given
        doThrow(InvalidRuntimeException.class)
                .when(movieRepository)
                .updateMovie(anyString(), anyString(), anyInt());
        Exception exception = null;

        // When
        try {
            movieService.updateMovie(TITLE1, GENRE, INVALID_RUNTIME);
        } catch (InvalidRuntimeException e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
    }

    @Test
    void testDeleteMovieWithExistingMovie() throws MovieNotFoundException {
        // When
        movieService.deleteMovie(TITLE1);

        // Then
        verify(movieRepository, times(1)).deleteMovie(TITLE1);
    }

    @Test
    void testDeleteMovieShouldReturnExceptionWhenDeleteNonExistingMovie()
            throws MovieNotFoundException {
        // Given
        doThrow(MovieNotFoundException.class)
                .when(movieRepository)
                .deleteMovie(anyString());

        // Then
        assertThrows(MovieNotFoundException.class, () ->{
            // When
            movieService.deleteMovie(TITLE1);
        });
    }
}