package streamo.server.uploader.bootstrap.service.helper;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import streamo.server.uploader.bootstrap.model.common.FileDto;
import streamo.server.uploader.bootstrap.model.response.DocDTO;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Slf4j
@Service
public class MinioConfig {
    @Value("${minio.data.endpoint}")
    private  String ip;
    @Value("${minio.data.access-key}")
    private  String accessKey;
    @Value("${minio.data.bucket}")
    private  String bucket;
    @Value("${minio.data.secret-key}")
    private  String secretKey;

    public MinioClient minioClient;
 public MinioConfig(){
     log.info("ip: {} , akey: {} , skey: {} , bucket: {}",ip,accessKey,secretKey,bucket);
   this.minioClient =  MinioClient.builder()
                    .endpoint("http://localhost:9000")
                    .credentials("tXrp11eNMfFf7fvt", "6WONgsmmYPZd8WccAZFlAt3Nhbco4sAc")
                    .build();}

    public boolean checkBucket() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
       return   minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
    }
    public List<FileDto> listAllObjectsInBucket() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        List<FileDto> data = new ArrayList<>();
        Iterable<Result<Item>> results  = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucket).recursive(false).build());

        for (Result<Item> item : results) {
            data.add(FileDto.builder()
                    .filename(item.get().objectName())
                    .size(item.get().size())
                    .url(item.get().objectName())
                    .build());
        }
return data;
    }

    public String uploadMultipartFile(MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        UUID uuid=UUID.randomUUID();
        minioClient.putObject(
                PutObjectArgs.builder().bucket(bucket).object(uuid.toString()+ LocalDateTime.now().toString()+file.getOriginalFilename()).stream(
                                file.getInputStream(), -1, 10485760)
                        .contentType(file.getContentType())
                        .build());

        return file.getOriginalFilename()+uuid.toString();
    }

    public DocDTO downloadFile(String objName){
     InputStream stream;
        try {
            stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(objName)
                    .build());
        } catch (Exception e) {
            log.error("Happened error when get list objects from minio: ", e);
            throw new RuntimeException(e);
        }
        log.info(objName);
        return DocDTO.builder().stream(stream).title(objName).build();
    }
}
