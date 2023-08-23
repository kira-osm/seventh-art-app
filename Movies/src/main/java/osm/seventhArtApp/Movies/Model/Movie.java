package osm.seventhArtApp.Movies.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "movies")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotBlank(message = "Title cannot be blank")
    @NotNull(message = "Title cannot be null")
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
