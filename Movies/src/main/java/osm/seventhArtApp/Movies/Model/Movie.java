package osm.seventhArtApp.Movies.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String title;

    private String image;

    private String description;

    private String category;

    private String duration;
    private String trailer;

    private String year;

    private int score;
    private String comments;
    private Date createdAt;
    private Date updatedAt;


}
