package osm.seventhArtApp.Movies.Model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "movies")
@Data
@Builder
public class Movie {

    @Id
    private String id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotNull(message = "Image cannot be null")
    private String image;

    @NotNull(message = "Description cannot be null")
    private String description;

    @NotNull(message = "Category cannot be null")
    private String category;

    @NotNull(message = "Duration cannot be null")
    private String duration;

    @NotNull(message = "Trailer cannot be null")
    private String trailer;

    @NotNull(message = "Year cannot be null")
    private String year;

    private int score;
    private String comments;
    private Date createdAt;
    private Date updatedAt;
}
