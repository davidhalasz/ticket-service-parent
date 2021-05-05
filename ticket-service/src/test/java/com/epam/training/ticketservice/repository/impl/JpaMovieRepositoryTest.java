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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class JpaMovieRepositoryTest {

    @Spy
    @InjectMocks
    private JpaMovieRepository movieRepository;
    @Mock
    private MapperRepository mapperRepository;
    @Mock
    private MovieDao movieDao;

    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_GENRE = "genre";
    private static final int VALID_RUNTIME = 100;

    private Movie movie;
    private Movie movie2;
    private List<Movie> movies;
    private Movie mappedMovie;

    private MovieEntity movieEntity;
    private MovieEntity movieEntity2;
    private List<MovieEntity> movieEntities;

    @BeforeEach
    void setUp() {
        movie = new Movie(MOVIE_TITLE, MOVIE_GENRE, VALID_RUNTIME);
        movieEntity = new MovieEntity(MOVIE_TITLE, MOVIE_GENRE, VALID_RUNTIME);
        movieEntities = List.of(movieEntity, movieEntity, movieEntity);
        movies = List.of(movie, movie2);
    }

}