package streamo.server.uploader.bootstrap.controller;

import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import streamo.server.uploader.bootstrap.model.common.FileDto;
import streamo.server.uploader.bootstrap.service.UploadService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.springframework.web.servlet.HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE;

@Slf4j
@RestController
public class UploadController {
    @Autowired
    UploadService service;
@PostMapping("/upload")
    public String uploadFile(@RequestBody MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
    log.info("request:at controller");
    return service.uploadObject(file);
}
@GetMapping("/list")
    public List<FileDto> listAll() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
    return service.getAllInBucket();
}

@GetMapping("/file/{obj-name}")

public ResponseEntity<Object> getFile(HttpServletRequest request, @PathVariable("obj-name")String objName) throws IOException {
    String pattern = (String) request.getAttribute(BEST_MATCHING_PATTERN_ATTRIBUTE);
    String filename = new AntPathMatcher().extractPathWithinPattern(pattern, request.getServletPath());
    return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(IOUtils.toByteArray(service.downloadFile(objName)));
}
}
