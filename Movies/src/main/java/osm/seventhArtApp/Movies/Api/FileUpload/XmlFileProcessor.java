package osm.seventhArtApp.Movies.Api.FileUpload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import osm.seventhArtApp.Movies.Model.Movie;
import osm.seventhArtApp.Movies.Repo.MovieRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class XmlFileProcessor {

    @Value("${xml.upload.directory}")
    private String xmlUploadDirectory;
    @Autowired
    private MovieRepository movieRepository;

    private final Lock fileProcessingLock = new ReentrantLock();

    @EventListener
    public void processXmlFile(ApplicationEvent event) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleWithFixedDelay(this::checkForNewXmlFiles, 0, 5, TimeUnit.SECONDS);
    }

    private void checkForNewXmlFiles() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();

            Path xmlDirectoryPath = Paths.get(xmlUploadDirectory);
            xmlDirectoryPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            WatchKey key;
            while ((key = watchService.take()) != null) {
                List<WatchEvent<?>> events = key.pollEvents();
                for (WatchEvent<?> event : events) {
                    Path fileName = (Path) event.context();
                    String filePath = xmlUploadDirectory + "/" + fileName;

                    if (fileName.toString().equals("movie.xml")) {
                        if (fileProcessingLock.tryLock()) {
                            try {
                                System.out.println("New XML file detected: " + fileName);


                                File xmlFile = new File(filePath);
                                XmlMapper xmlMapper = new XmlMapper();
                                Movie movie = xmlMapper.readValue(xmlFile, Movie.class);

                                ObjectMapper jsonMapper = new ObjectMapper();
                                String json = jsonMapper.writeValueAsString(movie);

                                ObjectMapper objectMapper = new ObjectMapper();
                                JsonNode jsonNode = objectMapper.readTree(json);
                                Movie movieData = objectMapper.treeToValue(jsonNode, Movie.class);

                                System.out.println(movieData);

                                // Uncomment to save the MovieEntity to MongoDB
                                // movieRepository.save(movieData);
                            } finally {
                                fileProcessingLock.unlock();
                            }
                        }
                    }
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
