package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dataaccess.entity.AdminEntity;
import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.equalTo;

class MovieServiceTest {

    private MovieService underTest;
    private final static String TITLE1 = "Satantango";
    private final static String TITLE2 = "Satantango";
    private final static String GENRE = "Drama";
    private static final int RUNTIME = 120;
    private final static String NAME = "admin";
    private final static String PASSWORD = "admin";

    private static final AdminEntity PRIVILIGED_ADMIN = new AdminEntity(NAME, PASSWORD, true);
    private static final MovieEntity MOVIE_ENTITY1 =  new MovieEntity(TITLE1, GENRE, RUNTIME);
    private static final MovieEntity MOVIE_ENTITY2 =  new MovieEntity(TITLE2, GENRE, RUNTIME);
    private static final List<MovieEntity> MOVIES = List.of(MOVIE_ENTITY1, MOVIE_ENTITY2);


    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new MovieService(movieRepository);
    }

    @Test
    public void testGetAllMovieShouldReturnsListOfExistingMovies() {
        // Given
        given(movieRepository.getAllMovie()).willReturn(MOVIES);

        // When
        List<MovieEntity> result = underTest.getAllMovie();

        // Then
        assertThat(result, equalTo(MOVIES));
    }


}