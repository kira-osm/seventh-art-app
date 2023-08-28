package osm.seventhArtApp.Movies.Api.FileUpload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import osm.seventhArtApp.Movies.Model.Movie;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class XmlFileProcessor {

    @Value("${xml.upload.directory}")
    private String xmlUploadDirectory;

    @EventListener
    public void processXmlFile(ApplicationEvent event) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleWithFixedDelay(this::checkForNewXmlFiles, 0, 5, TimeUnit.SECONDS);
    }

    private void checkForNewXmlFiles() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            File xmlFile = new File("C:/Users/user/seventh-art-app/upload-movie/movie.xml");

            // Deserialize XML to Java object
            XmlMapper xmlMapper = new XmlMapper();
            Movie movie = xmlMapper.readValue(xmlFile, Movie.class);

            // Serialize Java object to JSON
            ObjectMapper jsonMapper = new ObjectMapper();
            String json = jsonMapper.writeValueAsString(movie);

            // Display the JSON
            System.out.println(json);


            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    Path fileName = (Path) event.context();
                    System.out.println("New XML file detected: " + fileName);

                    String xmlFilePath = xmlUploadDirectory + "/" + fileName;
                    String xmlContent = Files.readString(Path.of(xmlFilePath));
                    System.out.println("XML Content:\n" + xmlContent);

                    // Convert XML to JSON using your preferred library
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(xmlContent);
                    String jsonContent = objectMapper.writeValueAsString(jsonNode);

                    System.out.println("JSON Content:\n" + jsonContent);
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
