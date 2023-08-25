package osm.seventhArtApp.Movies.Api.interne;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import osm.seventhArtApp.Movies.Business.api.MovieService;
import osm.seventhArtApp.Movies.Exceptions.MovieExceptions;
import osm.seventhArtApp.Movies.Model.Movie;

@RestController
@Slf4j
public class MovieDetailsWrite {

    @Autowired
    private MovieService movieService ;

    // Add a new movie with details.
    @PostMapping("/v1/internal/add-movie")
    public ResponseEntity<?> addMovie(@RequestBody Movie movie){

        Movie addMovie = movieService.createMovie(movie);

        log.info("The Movie " +movie.getTitle()+ " Have been added","");
        return ResponseEntity.status(HttpStatus.OK).body("The Movie " +movie.getTitle()+ " Have been added successfully");
    }


    // Update movie with details.
    @PutMapping("/v1/internal/update-movie-by-title")
    public ResponseEntity<Movie> updateMovieByTitle(
            @RequestParam String title,
            @RequestBody Movie movieToUpdate) {

        Movie updatedMovie = movieService.updateMovieByTitle(title, movieToUpdate);
        if (updatedMovie == null) {

            log.info("The Movie " +title + " not found","");
            //throw new MovieExceptions("No movies found for title: " + title);
        }
        log.info("The Movie " +title + " Have been Updated","");
        return ResponseEntity.ok(updatedMovie);
    }


}
