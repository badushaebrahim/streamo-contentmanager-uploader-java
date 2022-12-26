package streamo.server.uploader.bootstrap.controller;

import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import streamo.server.uploader.bootstrap.model.common.FileDto;
import streamo.server.uploader.bootstrap.model.response.DocDTO;
import streamo.server.uploader.bootstrap.service.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.springframework.web.servlet.HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE;

@Slf4j
@RestController
public class FileController {
    @Autowired
    FileService service;
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

public void  getFile(HttpServletResponse response, @PathVariable("obj-name")String objName) throws IOException {
//    String pattern = (String) request.getAttribute(BEST_MATCHING_PATTERN_ATTRIBUTE);
//    String filename = new AntPathMatcher().extractPathWithinPattern(pattern, request.getServletPath());
//    return ResponseEntity.ok()
//            .contentType(MediaType.APPLICATION_OCTET_STREAM)
//            .body(IOUtils.toByteArray(service.downloadFile(objName)));
    DocDTO data = service.downloadFile(objName);
response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+data.getTitle());
response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
    FileCopyUtils.copy(data.getStream(),response.getOutputStream());
}


@GetMapping("/life")
public boolean lifeCheck() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
    log.info("request:at controller");
    return service.ch();
}
}
