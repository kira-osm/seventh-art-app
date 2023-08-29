package osm.seventhArtApp.Movies.Api.interne;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import osm.seventhArtApp.Movies.Business.api.MovieService;
import osm.seventhArtApp.Movies.Exceptions.MovieExceptions;
import osm.seventhArtApp.Movies.Model.Movie;

@RestController
@Slf4j
public class MovieDetailsRead {

    @Autowired
    private MovieService movieService;

    private static final Logger log = LoggerFactory.getLogger(MovieDetailsWrite.class);



    @GetMapping("/v1/internal/all-movies")
    public ResponseEntity<Page<Movie>> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> movies = movieService.getAllMovies(pageable);

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/v1/internal/search-movies-by-title")
    public ResponseEntity<Page<Movie>> searchMoviesByTitleFuzzy(
            @RequestParam String title,
            Pageable pageable
    ) {
        Page<Movie> movies = movieService.searchMoviesByTitleFuzzy(title, pageable);

        if (movies.isEmpty()) {
            log.info("No movies found for title: " + title);
            //logger.info("No movies found for title: " + title);
            throw new MovieExceptions("No movies found for title: " + title);
        }

        return ResponseEntity.ok(movies);
    }


    //get the movie by the whole name
    @GetMapping("/v1/internal/get-movie-by-title")
    public ResponseEntity<Movie> getMovieByTitle(
            @RequestParam String title) {

        Movie movie = movieService.getMovieByTitle(title);
        if (movie == null) {
            log.info("No movies found for title: " + title);
            throw new MovieExceptions("No movies found for title: " + title);
        }
        return ResponseEntity.ok(movie);
    }

}
