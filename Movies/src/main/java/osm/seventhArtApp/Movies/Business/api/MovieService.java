package osm.seventhArtApp.Movies.Business.api;

import org.springframework.stereotype.Service;
import osm.seventhArtApp.Movies.Model.Movie;

import java.util.List;

public interface MovieService {

    Movie createMovie(Movie movie);
    Movie getMovieById(String id);
    Movie updateMovie(String movieId, Movie movie);
    List<Movie> getMovie();

    void deleteMovieById(String id);
}
