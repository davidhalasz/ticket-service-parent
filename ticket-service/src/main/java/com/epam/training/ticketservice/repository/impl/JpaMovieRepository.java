package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repository.MapperRepository;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RepositoryException.InvalidRuntimeException;
import com.epam.training.ticketservice.repository.RepositoryException.MovieAlreadyExistsException;
import com.epam.training.ticketservice.repository.RepositoryException.MovieNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@Slf4j
public class JpaMovieRepository implements MovieRepository {

    private final MovieDao movieDao;
    private final MapperRepository mapper;

    public JpaMovieRepository(MovieDao movieDao, MapperRepository mapper) {
        this.movieDao = movieDao;
        this.mapper = mapper;
    }

    @Override
    public void createMovie(Movie movie) throws MovieAlreadyExistsException, InvalidRuntimeException {
        if(isMovieExists(movie.getTitle())) {
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
    public List<Movie> getAllMovie() {
        List<MovieEntity> movieEntities = movieDao.findAll();
        return mapper.mapMovieEntities(movieEntities);
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

    @Override
    public Movie findMovieByTitle(String title) throws MovieNotFoundException{
        if(isMovieExists(title)) {
            return mapMovieEntity(movieDao.findMovieByTitle(title));
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
