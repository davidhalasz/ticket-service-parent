package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public List<MovieEntity> getAllMovie() {
        List<MovieEntity> movieEntities = movieDao.findAll();
        return movieEntities;
    }

    @Override
    public void updateMovie(String title, String genre, int runtime) {
        MovieEntity movieEntity = movieDao.findMovieByTitle(title);
        movieEntity.setTitle(title);
        movieEntity.setGenre(genre);
        movieEntity.setRuntime(runtime);
        movieDao.save(movieEntity);
    }

    @Override
    public void deleteMovie(String title) {
        MovieEntity movieEntity = movieDao.findMovieByTitle(title);
        movieDao.delete(movieEntity);
    }

}
