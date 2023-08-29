package osm.seventhArtApp.Movies.Api.interne;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import osm.seventhArtApp.Movies.Business.api.MovieService;
import osm.seventhArtApp.Movies.Model.Movie;

@RestController
@Slf4j
public class MovieDetailsWrite {

    @Autowired
    private MovieService movieService;
    private static final Logger log = LoggerFactory.getLogger(MovieDetailsWrite.class);



    // Add a new movie with details.
    @PostMapping("/v1/internal/add-movie")
    public ResponseEntity<?> addMovie(@RequestBody Movie movie) {

        String title = movie.getTitle();
        if (movieService.doesMovieExistByTitle(title)) {
            return ResponseEntity.badRequest().body("Movie with title " + title + " already exists.");
        }

        movieService.createMovie(movie);

        log.info("The Movie " + movie.getTitle() + " Have been added", "");
        return ResponseEntity.status(HttpStatus.OK).body("The Movie " + movie.getTitle() + " Have been added successfully");
    }


    // Update movie with details.
    @PutMapping("/v1/internal/update-movie-by-title")
    public ResponseEntity<Movie> updateMovieByTitle(@RequestParam String title, @RequestBody Movie movieToUpdate) {

        Movie updatedMovie = movieService.updateMovieByTitle(title, movieToUpdate);
        if (updatedMovie == null) {

            log.info("The Movie " + title + " not found", "");
            //throw new MovieExceptions("No movies found for title: " + title);
        }
        log.info("The Movie " + title + " Have been Updated", "");
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/v1/internal/delete-movie-by-title")
    public ResponseEntity<String> deleteMovieByTitle(@RequestParam String title) {
        movieService.deleteMovieByTitle(title);
        log.info("The Movie with title {} has been deleted", title);
        return ResponseEntity.ok("Movie with title " + title + " has been deleted");
    }


}
