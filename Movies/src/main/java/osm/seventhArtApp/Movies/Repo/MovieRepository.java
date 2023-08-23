package osm.seventhArtApp.Movies.Repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import osm.seventhArtApp.Movies.Model.Movie;


@Repository
public interface MovieRepository  extends MongoRepository<Movie , String> {
}
