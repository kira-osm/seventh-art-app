package osm.seventhArtApp.Movies.Business.api;

import osm.seventhArtApp.Movies.Model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {

    Movie createMovie(Movie movie);

    Page<Movie> getAllMovies(Pageable pageable);

    Movie getMovieByTitle(String title);

     Page<Movie> searchMoviesByTitleFuzzy(String title, Pageable pageable);

    Movie updateMovieByTitle(String title, Movie movieToUpdate);


    void deleteMovieById(String id);
}
