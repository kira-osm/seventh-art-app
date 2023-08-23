package osm.seventhArtApp.Movies.Business.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osm.seventhArtApp.Movies.Business.api.MovieService;
import osm.seventhArtApp.Movies.Model.Movie;
import osm.seventhArtApp.Movies.Repo.MovieRepository;

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
    public Movie getMovieById(String id) {
        return movieRepository.findById(id).orElse(null);
    }

    @Override
    public Movie updateMovie(String movieId , Movie movie) {
          movie.setId(movieId);
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> getMovie() {
        return null;
    }

    @Override
    public void deleteMovieById(String id) {
        movieRepository.deleteById(id);
    }
}
