package osm.seventhArtApp.Movies.Business.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import osm.seventhArtApp.Movies.Business.api.MovieService;
import osm.seventhArtApp.Movies.Business.mapper.FileUpdate;
import osm.seventhArtApp.Movies.Exceptions.MovieExceptions;
import osm.seventhArtApp.Movies.Model.Movie;
import osm.seventhArtApp.Movies.Repo.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import osm.seventhArtApp.Movies.Business.mapper.MovieMapper;


@Service
public class MovieImplementation implements MovieService {

    @Value("${xml.upload.directory}")
    private String xmlUploadDirectory;

    @Value("${spring.application.name}")
    private String fileNameXml;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public void createMovie(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public void addNewMovieFile(Movie movie) {
        try {
            String title = movie.getTitle();

            if (this.doesMovieExistByTitle(title)) {
                System.out.println("Movie with title " + title + " already exists.");
                return;
            }

            movieRepository.save(movie);
            System.out.println("Movie saved: " + movie);

            String newFileName = FileUpdate.renameXmlFile(movie.getId(), xmlUploadDirectory, fileNameXml);

            if (newFileName != null) {
                System.out.println("File renamed to: " + newFileName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Movie getMovieByTitle(String title) {
        return movieRepository.findByTitleIgnoreCase(title);
    }

    @Override
    public Page<Movie> searchMoviesByTitleFuzzy(String title, Pageable pageable) {
        return movieRepository.findByTitleFuzzy(title, pageable);
    }

    @Override
    public Movie updateMovieByTitle(String title, Movie movieToUpdate) {

        Movie existingMovie = movieRepository.findByTitleIgnoreCase(title);

        if (existingMovie != null) {
            MovieMapper.updateExistingMovie(existingMovie, movieToUpdate);
            return movieRepository.save(existingMovie);
        } else {
            throw new MovieExceptions("No movies found for title: " + title);
        }
    }

    @Override
    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    public void deleteMovieByTitle(String title) {
        movieRepository.deleteByTitleIgnoreCase(title);
    }

    @Override
    public boolean doesMovieExistByTitle(String title) {
        return movieRepository.existsByTitleIgnoreCase(title);
    }
}
