package streamo.server.uploader.bootstrap.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor


@Document(collection = "movies")
public class MoviesEntity {
}
