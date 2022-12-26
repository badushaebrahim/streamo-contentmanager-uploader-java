package streamo.server.uploader.bootstrap.model.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDto {
String filename;
long size;
String url;
}
