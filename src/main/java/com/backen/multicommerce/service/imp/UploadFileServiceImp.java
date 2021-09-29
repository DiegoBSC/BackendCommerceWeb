package com.backen.multicommerce.service.imp;

import com.backen.multicommerce.entity.ConfigurationApp;
import com.backen.multicommerce.repository.ConfigurationAppRepository;
import com.backen.multicommerce.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImp implements UploadFileService {

    @Autowired
    ConfigurationAppRepository configurationAppRepository;

    private final Logger log = LoggerFactory.getLogger(UploadFileServiceImp.class);

    @Override
    public Resource charge(String nameFile, String type) throws MalformedURLException {
        Path pathFile = getPath(nameFile, type);
        log.info(pathFile.toString());

        Resource resource = new UrlResource(pathFile.toUri());

        if (!resource.exists() && !resource.isReadable()) {
            return null;
        }
        return resource;
    }

    @Override
    public String copy(MultipartFile fle, String type) throws IOException {
        String nameFile = UUID.randomUUID().toString() + "_" + fle.getOriginalFilename().replace(" ", "");

        Path pathFile = getPath(nameFile, type);
        log.info(pathFile.toString());

        if (Files.notExists(pathFile)) {
            log.info("Not Folder");
            File file = new File(getPathFolder(type));
            file.mkdirs();
        }

        Files.copy(fle.getInputStream(), pathFile);

        return nameFile;
    }

    @Override
    public boolean delete(String nameFile, String type) {
        if (nameFile != null && nameFile.length() > 0) {
            Path pathFolderOld = getPath(nameFile, type);
            File fileOld = pathFolderOld.toFile();
            if (fileOld.exists() && fileOld.canRead()) {
                fileOld.delete();
                return true;
            }
        }
        return false;
    }

    @Override
    public Path getPath(String nameFile, String type) {
        return Paths.get(getPathFolder(type)).resolve(nameFile).toAbsolutePath();
    }

    public String getPathFolder(String type) {
        String separator = System.getProperty("file.separator");
        ConfigurationApp configuracion = configurationAppRepository.findById(1).orElse(null);
        return configuracion.getValue() + separator + type;
    }
}
