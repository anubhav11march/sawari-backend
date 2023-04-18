package com.tzs.marshall.filesystem;

import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.bean.ProfileDetails;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import static com.tzs.marshall.constants.Constants.FILE_FORMATS;
import static com.tzs.marshall.constants.Constants.UPLOAD_DIR;

@Component
public class FileHelper {
    private final static Logger log = LoggerFactory.getLogger(FileHelper.class);

    public void fetchAndUploadProfileDetails(ProfileDetails userDetails) {
        FileBean fileBean;
        if (userDetails.getProfilePhoto() != null) {
            fileBean = uploadFileHelper(userDetails.getProfilePhoto(), userDetails.getUserId(), userDetails.getRoleName(), "profile");
            userDetails.setProfilePhotoName(fileBean.getFileName());
            userDetails.setProfilePhotoPath(fileBean.getPath());
            userDetails.setProfilePhotoSize(fileBean.getSize());
        }

        if (userDetails.getAadharBackPhoto() != null && userDetails.getAadharFrontPhoto() != null) {
            fileBean = uploadFileHelper(userDetails.getAadharBackPhoto(), userDetails.getUserId(), userDetails.getRoleName(), "aadhar");
            userDetails.setAadharBackPhotoName(fileBean.getFileName());
            userDetails.setAadharBackPhotoPath(fileBean.getPath());
            userDetails.setAadharBackPhotoSize(fileBean.getSize());

            fileBean = uploadFileHelper(userDetails.getAadharFrontPhoto(), userDetails.getUserId(), userDetails.getRoleName(), "aadhar");
            userDetails.setAadharFrontPhotoName(fileBean.getFileName());
            userDetails.setAadharFrontPhotoPath(fileBean.getPath());
            userDetails.setAadharFrontPhotoSize(fileBean.getSize());
        }

        if (userDetails.getRickshawFrontPhoto() != null && userDetails.getRickshawBackPhoto() != null
                && userDetails.getRickshawSidePhoto() != null) {
            fileBean = uploadFileHelper(userDetails.getRickshawFrontPhoto(), userDetails.getUserId(), userDetails.getRoleName(), "rickshaw");
            userDetails.setRickshawFrontPhotoName(fileBean.getFileName());
            userDetails.setRickshawFrontPhotoPath(fileBean.getPath());
            userDetails.setRickshawFrontPhotoSize(fileBean.getSize());

            fileBean = uploadFileHelper(userDetails.getRickshawBackPhoto(), userDetails.getUserId(), userDetails.getRoleName(), "rickshaw");
            userDetails.setRickshawBackPhotoName(fileBean.getFileName());
            userDetails.setRickshawBackPhotoPath(fileBean.getPath());
            userDetails.setRickshawBackPhotoSize(fileBean.getSize());

            fileBean = uploadFileHelper(userDetails.getRickshawSidePhoto(), userDetails.getUserId(), userDetails.getRoleName(), "rickshaw");
            userDetails.setRickshawSidePhotoName(fileBean.getFileName());
            userDetails.setRickshawSidePhotoPath(fileBean.getPath());
            userDetails.setRickshawSidePhotoSize(fileBean.getSize());
        }
    }

    public FileBean uploadFileHelper(MultipartFile file, Long userId, String roleName, String subFolder) {
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
                Path path = Paths.get(DBProperties.properties.getProperty(UPLOAD_DIR) + roleName
                        + File.separator + userId + File.separator + subFolder + File.separator + fileName);
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

    public void serveImageInResponse(String imageName, HttpServletResponse response, String dirPathI) {
        dirPathI = dirPathI.replaceAll("\\\\", "/");
        try {
            File savePath = new File(dirPathI);
            int imageSize = 0;
            for (int i = 0; i < 10; i++) {
                if (!savePath.exists()) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                String contentType = "application/octet-stream";
                imageSize = (int) savePath.length();
                response.setContentType(contentType);
                response.setHeader("Content_length", String.valueOf(savePath.length()));
                response.setHeader("Content-Disposition", "inline: filename=\"" + imageName + "\"");
                try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(savePath), imageSize);
                     BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream(), imageSize)) {
                    byte[] buffer = new byte[imageSize];
                    int byteReads;
                    while ((byteReads = input.read(buffer)) > 0) {
                        output.write(buffer, 0, byteReads);
                    }
                }
            }
            log.info("image served");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new ApiException(ex.getMessage());
        }
    }
}
