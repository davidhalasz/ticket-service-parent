package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repository.MapperRepository;
import com.epam.training.ticketservice.repository.RepositoryException.InvalidRuntimeException;
import com.epam.training.ticketservice.repository.RepositoryException.MovieAlreadyExistsException;
import com.epam.training.ticketservice.repository.RepositoryException.MovieNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaMovieRepositoryTest {

    @Spy
    @InjectMocks
    private JpaMovieRepository movieRepository;
    @Mock
    private MovieDao movieDao;

    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_GENRE = "genre";
    private static final int VALID_RUNTIME = 100;
    private static final String MOVIE_EXISTS_MSG = "Movie already exists";

    private Movie movie;
    private Movie movie2;
    private List<Movie> movies;
    private Movie mappedMovie;

    private MovieEntity movieEntity;
    private MovieEntity movieEntity2;
    private List<MovieEntity> movieEntities;

    private static final MovieAlreadyExistsException MOVIE_ALREADY_EXISTS_EXCEPTION
            = new MovieAlreadyExistsException(MOVIE_EXISTS_MSG);

    @BeforeEach
    void setUp() {
        movie = new Movie(MOVIE_TITLE, MOVIE_GENRE, VALID_RUNTIME);
        movieEntity = new MovieEntity(MOVIE_TITLE, MOVIE_GENRE, VALID_RUNTIME);
        movieEntities = List.of(movieEntity, movieEntity, movieEntity);
        movies = List.of(movie, movie2);
    }


}