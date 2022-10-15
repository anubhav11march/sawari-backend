package com.tzs.marshall.filesystem;

import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import static com.tzs.marshall.constants.Constants.*;

@Component
public class FileHelper {
    private final static Logger log = LoggerFactory.getLogger(FileHelper.class);

    public FileBean uploadFileHelper(MultipartFile file,  Long userId) {
        FileBean fileBean = new FileBean();
        fileBean.setStatus(Boolean.FALSE);
        String contentType = Objects.requireNonNull(file.getContentType());
        log.info("Validating File Format, [File Type]: {}", contentType);
        if (DBProperties.properties.getProperty(FILE_FORMATS).contains(contentType)) {
            log.info("File Format accepted.");
            try {
                int randomPin = (int) (Math.random() * 9000) + 1000;
                String uuid = String.valueOf(randomPin);
                String fileName = userId + "-" + uuid + "-"
                        + Objects.requireNonNull(file.getOriginalFilename()).trim();
                log.info("File Name Created: {}", fileName);
                Path path = Paths.get(DBProperties.properties.getProperty(UPLOAD_DIR) + userId + File.separator + fileName);
                log.info("Path: {}", path.toString());
                File contentSaveDir = new File(String.valueOf(path));
                if (!contentSaveDir.exists()) {
                    log.info("Creating Directories...");
                    boolean mkdirs = contentSaveDir.mkdirs();
                    if (!mkdirs) log.error("Cannot Create Directories. Please Check.");
                }
                log.info("Copying file to the location...");
                Files.copy(file.getInputStream(),
                        path,
                        StandardCopyOption.REPLACE_EXISTING);

                log.info("File Saved to directory.");
                fileBean.setFileName(fileName);
                fileBean.setFileFormat(file.getContentType());
                fileBean.setSize(file.getSize());
                fileBean.setPath(path.toString());
                fileBean.setStatus(Boolean.TRUE);
                log.info("File Bean: {}", fileBean);
            } catch (IOException e) {
                log.error("Unable to save file on server. [ERROR]: " + e.getMessage());
                throw new ApiException(MessageConstants.UNABLE_TO_SAVE_FILE);
            }
        } else
            throw new ApiException(contentType + MessageConstants.FILE_FORMAT_ERR);

        return fileBean;
    }

    public Resource loadFileAsResourceHelper(String path) {
        log.info("Normalizing File Path...");
        try {
            Path filePath = Paths.get(path).normalize();
            log.info("Preparing file as a resource...");
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                log.info("Resource: " + resource);
                return resource;
            } else {
                log.error("File does not exist.");
                throw new ApiException(MessageConstants.NO_FILE_FOUND);
            }
        } catch (MalformedURLException ex) {
            log.error("File does not exist. [ERROR]: "+ex.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }
}
