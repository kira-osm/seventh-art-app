package osm.seventhArtApp.Movies.Controllers.interne;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import osm.seventhArtApp.Movies.Business.api.MovieService;
import osm.seventhArtApp.Movies.Model.Movie;
import javax.validation.Valid;

@RestController
@Slf4j
public class MovieDetails {

    @Autowired
    private MovieService movieService ;

    @PostMapping("/v1/internal/add-movie")
    @Validated
    public ResponseEntity<?> addMovie(@RequestBody @Valid Movie movie , BindingResult result){

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }
        Movie addMovie = movieService.createMovie(movie);

        log.info("The Movie " +movie.getTitle()+ " Have been added","");
        return ResponseEntity.status(HttpStatus.OK).body(addMovie);
    }
}
