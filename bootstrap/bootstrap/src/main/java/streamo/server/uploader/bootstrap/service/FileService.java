package streamo.server.uploader.bootstrap.service;

import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import streamo.server.uploader.bootstrap.model.common.FileDto;
import streamo.server.uploader.bootstrap.model.response.DocDTO;
import streamo.server.uploader.bootstrap.service.helper.MinioConfig;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FileService {
    @Value("${minio.data.endpoint}")
    private  String ip;
    @Value("${minio.data.access-key}")
    private  String accessKey;
//    @Value("${minio.data.bucket}")
    private  String bucket ="stramo";
    @Value("${minio.data.secret-key}")
    private  String secretKey;
@Autowired
    MinioConfig minioConfig;

public String  uploadObject(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
    return minioConfig.uploadMultipartFile(file);
}
public List<FileDto> getAllInBucket() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
    return minioConfig.listAllObjectsInBucket();
}

    public boolean  ch () throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("ip: {} , akey: {} , skey: {} , bucket: {}",ip,accessKey,secretKey,bucket);

        return minioConfig.checkBucket();
    }

    public DocDTO downloadFile(String objName){
//    Optional<InputStream> rep = Optional.ofNullable(minioConfig.downloadFile(objName));
//       if(rep.isPresent()){
//           return rep.get();
//       }
//       throw new RuntimeException("null");
//    }
        try{
return minioConfig.downloadFile(objName);}
catch (Exception e){
    log.error("Happened error when get list objects from minio: ", e);
    throw new RuntimeException(e);
}
}
}
