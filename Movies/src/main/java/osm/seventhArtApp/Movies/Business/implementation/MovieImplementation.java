package osm.seventhArtApp.Movies.Business.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    private MovieRepository movieRepository;

    @Value("${xml.upload.directory}")
    private String xmlUploadDirectory;

    @Value("${spring.application.name}")
    private String fileNameXml;

    @Value("${xml.upload.destination}")
    private String xmlUploadDestination;

    private static final Logger log = LoggerFactory.getLogger(MovieImplementation.class);


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
                FileUpdate.deleteXmlFile(fileNameXml, xmlUploadDirectory);
                return;
            }

            movieRepository.save(movie);

            log.info("Movie {} saved successfully", movie.getTitle());

            String newFileName = FileUpdate.renameXmlFile(movie.getTitle(), xmlUploadDirectory, fileNameXml);


            if (newFileName != null) {
                System.out.println("File renamed to: " + newFileName);
                FileUpdate.moveXmlFileToDestination(newFileName, xmlUploadDirectory, xmlUploadDestination);
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
