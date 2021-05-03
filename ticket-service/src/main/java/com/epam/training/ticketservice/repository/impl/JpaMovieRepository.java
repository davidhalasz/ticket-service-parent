package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaMovieRepository implements MovieRepository {

    private MovieDao movieDao;

    public JpaMovieRepository(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public void createMovie(MovieEntity movie) {
        MovieEntity newMovie = new MovieEntity(movie.getTitle(), movie.getGenre(), movie.getRuntime());
        movieDao.save(newMovie);
    }
    
}
