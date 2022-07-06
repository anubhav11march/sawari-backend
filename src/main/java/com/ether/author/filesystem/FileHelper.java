package com.ether.author.filesystem;

import com.ether.author.bean.AESHProperties;
import com.ether.author.constants.MessageConstants;
import com.ether.author.error.ApiException;
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

@Component
public class FileHelper {
    private final static Logger log = LoggerFactory.getLogger(FileHelper.class);

    public FileBean uploadFileHelper(FileBean fileBean) {
        fileBean.setStatus(Boolean.FALSE);
        String contentType = Objects.requireNonNull(fileBean.getFile().getContentType());
        log.info("Validating File Format, [File Type]: {}", contentType);
        if (AESHProperties.FILE_FORMATS.contains(contentType)) {
            log.info("File Format accepted.");
            MultipartFile file = fileBean.getFile();
            try {
                int randomPin = (int) (Math.random() * 9000) + 1000;
                String uuid = String.valueOf(randomPin);
                String fileName = fileBean.getFileUserId() + uuid
                        + fileBean.getRequestServeDate().toString().replaceAll("-", "") + "-"
                        + Objects.requireNonNull(file.getOriginalFilename()).trim();
                log.info("File Name Created: {}", fileName);
                Path path = Paths.get(AESHProperties.UPLOAD_DIR + fileBean.getFileUserId() + File.separator
                        + fileName.substring(fileName.indexOf(".")) + File.separator + fileName);
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
                if (fileBean.getLanguage() == null) fileBean.setLanguage("English");
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
