package com.backen.multicommerce.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface UploadFileService {

    Resource charge(String nameFile, String type) throws MalformedURLException;

    String copy(MultipartFile fle, String type) throws IOException;

    boolean delete(String nameFile, String type);

    Path getPath(String nameFile, String type);
}
