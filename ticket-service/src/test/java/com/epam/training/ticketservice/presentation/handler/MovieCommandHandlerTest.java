package com.epam.training.ticketservice.presentation.handler;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.RepositoryException.*;
import com.epam.training.ticketservice.service.AdminService;
import com.epam.training.ticketservice.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class MovieCommandHandlerTest {

    @InjectMocks
    private MovieCommandHandler movieCommandHandler;
    @Mock
    private MovieService movieService;
    @Mock
    private AdminService adminService;


    private final static String UNPRIVILIGED_MSG = "You are not signed in";
    private final static String MOVIE_ADDED = "Movie added";
    private final static String MOVIE_UPDATED = "Movie updated";
    private final static String MOVIE_DELETED = "Movie deleted";
    private final static String INVALID_RUNTIME_MSG = "Runtime cannot be null";
    private final static String MOVIE_NOT_FOUND = "Movie not found";
    private final static String TITLE = "movie title";
    private final static String GENRE = "movie genre";
    private final static int RUNTIME = 20;
    private final static int INV_RUNTIME = 0;
    private final static String MOVIE_EXISTS = "Movie already exists";
    private final static String EMPTY_LIST_MSG = "There are no movies at the moment";


    private static final MovieAlreadyExistsException MOVIE_ALREADY_EXISTS_EXCEPTION
            = new MovieAlreadyExistsException(MOVIE_EXISTS);
    private static final InvalidRuntimeException INVALID_RUNTIME_EXCEPTION
            = new InvalidRuntimeException(INVALID_RUNTIME_MSG);
    private static final MovieNotFoundException MOVIE_NOT_FOUND_EXCEPTION
            = new MovieNotFoundException(MOVIE_NOT_FOUND);
    private final static Movie movie = new Movie(TITLE, GENRE, RUNTIME);

    private final static List<Movie> movies = List.of(movie, movie);


    @Test
    void testCreateMovieWhenAdminIsSignedIn()
            throws MovieAlreadyExistsException, InvalidRuntimeException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);

        // When
        String current = movieCommandHandler.createMovie(TITLE, GENRE, RUNTIME);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(movieService, times(1)).addMovie(TITLE, GENRE, RUNTIME);
        assertThat(current, equalTo(MOVIE_ADDED));
    }

    @Test
    void testCreateMovieReturnErrorWhenAdminIsNotSignedIn() throws MovieAlreadyExistsException, InvalidRuntimeException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(false);

        // When
        String current = movieCommandHandler.createMovie(TITLE, GENRE, RUNTIME);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        assertThat(current, equalTo(UNPRIVILIGED_MSG));
    }

    @Test
    void testCreateMovieShouldReturnExceptionWhenMovieAlreadyExists() throws MovieAlreadyExistsException, InvalidRuntimeException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);
        doThrow(MOVIE_ALREADY_EXISTS_EXCEPTION)
                .when(movieService)
                .addMovie(any(), any(), anyInt());

        // When
        String current = movieCommandHandler.createMovie(TITLE, GENRE, RUNTIME);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(movieService, times(1)).addMovie(TITLE, GENRE, RUNTIME);
        assertThat(current, equalTo(MOVIE_EXISTS));
    }

    @Test
    void testCreateMovieShouldReturnExceptionWhenMovieRuntimeIsInvalid()
            throws MovieAlreadyExistsException, InvalidRuntimeException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);
        doThrow(INVALID_RUNTIME_EXCEPTION)
                .when(movieService)
                .addMovie(any(), any(), anyInt());

        // When
        String current = movieCommandHandler.createMovie(TITLE, GENRE, RUNTIME);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(movieService, times(1)).addMovie(TITLE, GENRE, RUNTIME);
        assertThat(current, equalTo(INVALID_RUNTIME_MSG));
    }

    @Test
    void testlistMoviesShouldReturnEmptyMovieListMessage() {
        // Given
        given(movieService.getAllMovie()).willReturn(List.of());

        // When
        String current = movieCommandHandler.listMovies();

        // Then
        verify(movieService, times(1)).getAllMovie();
        assertThat(current, equalTo(EMPTY_LIST_MSG));
    }

    @Test
    void testUpdateMovieWhenAdminIsSignedIn()
            throws MovieAlreadyExistsException, InvalidRuntimeException, MovieNotFoundException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);

        // When
        String current = movieCommandHandler.updateMovie(TITLE, GENRE, RUNTIME);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(movieService, times(1)).updateMovie(TITLE, GENRE, RUNTIME);
        assertThat(current, equalTo(MOVIE_UPDATED));
    }

    @Test
    void testUpdateMovieReturnErrorWhenAdminIsNotSignedIn() throws InvalidRuntimeException, MovieNotFoundException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(false);

        // When
        String current = movieCommandHandler.updateMovie(TITLE, GENRE, RUNTIME);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        assertThat(current, equalTo(UNPRIVILIGED_MSG));
    }

    @Test
    void testUpdateMovieShouldReturnExceptionWhenMovieIsNotExist() throws InvalidRuntimeException, MovieNotFoundException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);
        doThrow(MOVIE_NOT_FOUND_EXCEPTION)
                .when(movieService)
                .updateMovie(any(), any(), anyInt());

        // When
        String current = movieCommandHandler.updateMovie(TITLE, GENRE, RUNTIME);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(movieService, times(1)).updateMovie(TITLE, GENRE, RUNTIME);
        assertThat(current, equalTo(MOVIE_NOT_FOUND));
    }

    @Test
    void testUpdateMovieShouldReturnExceptionWhenRuntimeIsInvalid() throws InvalidRuntimeException, MovieNotFoundException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);
        doThrow(INVALID_RUNTIME_EXCEPTION)
                .when(movieService)
                .updateMovie(any(), any(), anyInt());

        // When
        String current = movieCommandHandler.updateMovie(TITLE, GENRE, RUNTIME);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(movieService, times(1)).updateMovie(TITLE, GENRE, RUNTIME);
        assertThat(current, equalTo(INVALID_RUNTIME_MSG));
    }

    @Test
    void testDeleteMovieWhenAdminIsSignedIn()
            throws MovieNotFoundException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);

        // When
        String current = movieCommandHandler.deleteMovie(TITLE);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(movieService, times(1)).deleteMovie(TITLE);
        assertThat(current, equalTo(MOVIE_DELETED));
    }

    @Test
    void testDeleteMovieReturnErrorWhenAdminIsNotSignedIn() throws MovieNotFoundException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(false);

        // When
        String current = movieCommandHandler.deleteMovie(TITLE);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        assertThat(current, equalTo(UNPRIVILIGED_MSG));
    }

    @Test
    void testListMoviesShouldReturnListOfRooms() {
        // Given
        when(movieService.getAllMovie()).thenReturn(movies);

        // When
        String actual = movieCommandHandler.listMovies();

        // Then
        StringBuilder builder = new StringBuilder();
        for (Movie movie : movies) {
            builder.append(movie);
        }
        assertThat(actual, equalTo(builder.toString()));
    }

}