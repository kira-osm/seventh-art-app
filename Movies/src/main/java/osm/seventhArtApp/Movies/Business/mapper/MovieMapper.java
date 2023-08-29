package osm.seventhArtApp.Movies.Business.mapper;

import osm.seventhArtApp.Movies.Model.Movie;

import java.lang.reflect.Field;
import java.util.Date;


public class MovieMapper {
    public static void updateExistingMovie(Movie existingMovie, Movie updatedMovie) {
        Field[] fields = Movie.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object updatedValue = field.get(updatedMovie);
                if (updatedValue != null) {
                    field.set(existingMovie, updatedValue);
                }
            } catch (IllegalAccessException e) {
                // Handle exception
            }
        }
        existingMovie.setUpdatedAt(new Date());
    }
}
