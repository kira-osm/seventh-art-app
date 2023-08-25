package osm.seventhArtApp.Movies.Api.Advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import osm.seventhArtApp.Movies.Exceptions.MovieExceptions;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieExceptions.class)
    public ResponseEntity<String> handleMovieNotFoundException(MovieExceptions ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
