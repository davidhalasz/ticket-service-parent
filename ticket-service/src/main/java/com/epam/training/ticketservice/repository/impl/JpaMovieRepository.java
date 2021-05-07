package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repository.mapper.MapperInterface;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.exceptions.InvalidRuntimeException;
import com.epam.training.ticketservice.exceptions.MovieAlreadyExistsException;
import com.epam.training.ticketservice.exceptions.MovieNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class JpaMovieRepository implements MovieRepository {

    private final MovieDao movieDao;
    private final MapperInterface mapper;

    @Override
    public void createMovie(Movie movie)
            throws MovieAlreadyExistsException, InvalidRuntimeException {

        if (isMovieExists(movie.getTitle())) {
            throw new MovieAlreadyExistsException("Movie already exists");
        }

        if (movie.getRuntime() < 1) {
            throw new InvalidRuntimeException("Runtime is invalid");
        }

        MovieEntity newMovie = new MovieEntity(
                movie.getTitle(),
                movie.getGenre(),
                movie.getRuntime()
        );
        movieDao.save(newMovie);
    }

    @Override
    public List<Movie> getAllMovies() {
        List<MovieEntity> movieEntities = movieDao.findAll();
        return mapper.mapMovieEntities(movieEntities);
    }

    @Override
    public void updateMovie(String title, String genre, int runtime)
            throws MovieNotFoundException, InvalidRuntimeException {

        if (runtime < 0) {
            throw new InvalidRuntimeException("Runtime cannot be null");
        }

        if (isMovieExists(title)) {
            MovieEntity movieEntity = movieDao.findMovieByTitle(title);
            movieEntity.setGenre(genre);
            movieEntity.setRuntime(runtime);
            movieDao.save(movieEntity);
        } else {
            throw new MovieNotFoundException("Movie not found");
        }
    }

    @Override
    public void deleteMovie(String title)
            throws MovieNotFoundException {

        if (isMovieExists(title)) {
            MovieEntity movieEntity = movieDao.findMovieByTitle(title);
            movieDao.delete(movieEntity);
        } else {
            throw new MovieNotFoundException("Movie not found");
        }
    }

    @Override
    public Movie getMovieByTitle(String title)
            throws MovieNotFoundException {

        if (isMovieExists(title)) {
            return mapper.mapMovieEntity(movieDao.findMovieByTitle(title));
        } else {
            throw new MovieNotFoundException("There is no such movie");
        }
    }

    @Override
    public boolean isMovieExists(String title) {

        Optional<MovieEntity> movieEntity = movieDao.findById(title);
        return movieEntity.isPresent();
    }

}
