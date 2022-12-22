package streamo.server.uploader.bootstrap.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DocDTO {
    private String  title;
    private InputStream stream;
}
