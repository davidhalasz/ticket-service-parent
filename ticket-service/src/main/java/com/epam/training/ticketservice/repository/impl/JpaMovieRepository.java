package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.ServiceException.InvalidRuntimeException;
import com.epam.training.ticketservice.service.ServiceException.MovieAlreadyExistsException;
import com.epam.training.ticketservice.service.ServiceException.MovieNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaMovieRepository implements MovieRepository {

    private MovieDao movieDao;

    public JpaMovieRepository(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public void createMovie(Movie movie) throws MovieAlreadyExistsException, InvalidRuntimeException {
        if(isMovieExists(movie.getTitle())) {
            throw new MovieAlreadyExistsException("Movie already exists");
        } else {
            if (movie.getRuntime() < 1) {
                throw new InvalidRuntimeException("Runtime is invalid");
            } else {
                MovieEntity newMovie = new MovieEntity(movie.getTitle(), movie.getGenre(), movie.getRuntime());
                movieDao.save(newMovie);
            }
        }
    }

    @Override
    public List<Movie> getAllMovie() {
        List<MovieEntity> movieEntities = movieDao.findAll();
        return mapMovieEntities(movieEntities);
    }

    private List<Movie> mapMovieEntities(List<MovieEntity> movieEntities) {
        return movieEntities.stream()
                .map(this::mapMovieEntity)
                .collect(Collectors.toList());
    }

    private Movie mapMovieEntity(MovieEntity movieEntity) {
        return new Movie(movieEntity.getTitle(), movieEntity.getGenre(), movieEntity.getRuntime());
    }

    @Override
    public Movie updateMovie(String title, String genre, int runtime) throws MovieNotFoundException, InvalidRuntimeException {
        if (runtime < 0) {
            throw new InvalidRuntimeException("Runtime cannot be null");
        } else if (isMovieExists(title)) {
            MovieEntity movieEntity = movieDao.findMovieByTitle(title);
            movieEntity.setTitle(title);
            movieEntity.setGenre(genre);
            movieEntity.setRuntime(runtime);
            movieDao.save(movieEntity);
        } else {
            throw new MovieNotFoundException("Movie not found");
        }

        return null;
    }

    @Override
    public Movie deleteMovie(String title) throws MovieNotFoundException {
        if (isMovieExists(title)) {
            MovieEntity movieEntity = movieDao.findMovieByTitle(title);
            movieDao.delete(movieEntity);
        } else {
            throw new MovieNotFoundException("Movie not found");
        }
        return null;
    }

    private boolean isMovieExists(String title) {
        Optional<MovieEntity> movieEntity = Optional.ofNullable(movieDao.findMovieByTitle(title));
        return movieEntity.isPresent();
    }

}
