package osm.seventhArtApp.Movies.Repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import osm.seventhArtApp.Movies.Model.Movie;

import java.util.List;


@Repository
public interface MovieRepository  extends MongoRepository<Movie , String> {

    @Query("{'title': {$regex : ?0, $options: 'i'}}")
    Page<Movie> findByTitleFuzzy(String title, Pageable pageable);

    Movie findByTitleIgnoreCase(String title);
}
