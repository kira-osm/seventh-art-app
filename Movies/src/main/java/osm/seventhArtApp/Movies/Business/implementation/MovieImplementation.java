package osm.seventhArtApp.Movies.Business.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osm.seventhArtApp.Movies.Business.api.MovieService;
import osm.seventhArtApp.Movies.Exceptions.MovieExceptions;
import osm.seventhArtApp.Movies.Model.Movie;
import osm.seventhArtApp.Movies.Repo.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import osm.seventhArtApp.Movies.Business.mapper.MovieMapper;

import java.util.List;
@Service
public class MovieImplementation implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie getMovieByTitle(String title) {
        return movieRepository.findByTitleIgnoreCase(title);
    }

    @Override
    public Page<Movie> searchMoviesByTitleFuzzy(String title, Pageable pageable) {
        return movieRepository.findByTitleFuzzy(title, pageable);
    }

    @Override
    public Movie updateMovieByTitle(String title, Movie movieToUpdate) {

        Movie existingMovie = movieRepository.findByTitleIgnoreCase(title);

        if (existingMovie != null) {
            MovieMapper.updateExistingMovie(existingMovie, movieToUpdate);
            return movieRepository.save(existingMovie);
        } else {
            throw new MovieExceptions("No movies found for title: " + title);
        }
    }


    @Override
    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    public void deleteMovieById(String id) {
        movieRepository.deleteById(id);
    }
}
