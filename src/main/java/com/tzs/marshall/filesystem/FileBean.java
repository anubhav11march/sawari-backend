package com.tzs.marshall.filesystem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileBean {

    //id of that file which author uploads to the server
    private Long authorFileId;
    //id of the report which preliminary or main editor upload for a particular author and file
    private Long adminFileId;
    //id of the AESH user whoever upload a file
    private Long fileUserId;
    //id of author for which a preliminary or main editor upload their report for a particular file.
    private Long authorUserId;
    private String userName;
    private MultipartFile file;
    private String fileName;
    private String fileFormat;
    private String reportType;
    private String topic;
    private String category;
    private String description;
    private String language;
    private String wordsCount;
    private String numberOfFigures;
    private String requestedServices;
    private String proposedServices;
    private String fileStatus;
    private String path;
    private Long size;
    private Timestamp uploadDate;
    private Date requestServeDate;
    private Timestamp modifyDate;
    private double estimatedAmount;
    private String uploadBy;
    private boolean is_deleted;
    private boolean status;

    private Resource resource;

    public void setCategory(String category) {
        this.category = category;
    }

    public FileBean(Long fileUserId, MultipartFile file, String topic, String category, String description,
                    Date requestServeDate) {
        this.fileUserId = fileUserId;
        this.file = file;
        this.topic = topic;
        this.category = category;
        this.description = description;
        this.requestServeDate = requestServeDate;
    }

}
