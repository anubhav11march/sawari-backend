package com.tzs.marshall.filesystem;

import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileHelper fileHelper;

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public FileBean uploadFileHandler(FileBean fileBean) {
        log.info("Validating File before upload...");
        fileBean = fileHelper.uploadFileHelper(fileBean.getFile(), fileBean.getFileUserId());
        try {
            if (fileBean.isStatus() == Boolean.TRUE) {
                log.info("Uploading File Details to DB...");
                fileRepository.saveFile(fileBean);
                log.info("File Saved to DB.");
            }
        } catch (Exception exception) {
            log.error(String.format("Unable to upload [%s]", fileBean.getFileName()));
            log.error(exception.getMessage());
            throw new ApiException(String.format(MessageConstants.FILE_UPLOAD_ERR, fileBean.getFileName()));
        }
        return fileBean;
    }

    @Override
    public List<FileBean> findFilesHandler(Long fileUserId, String uploadBy) {
        log.info("finding files for userId: " + fileUserId);
        List<FileBean> files = fileRepository.findFiles(fileUserId, uploadBy);
        if (files.size() == 0) {
            log.error("Zero files fetched from DB. " + files);
            throw new ApiException(MessageConstants.NO_FILE_FOUND);
        }
        log.info(files.size() + " Files are available.");
        return files;
    }

    @Override
    public FileBean findFileByIdHandler(Long fileUserId, Long fileId, String uploadBy) {
        log.info(String.format("Fetching FileId:[%s] for UserId:[%s]", fileId, fileUserId));
        List<FileBean> fileById = fileRepository.findFileById(fileUserId, fileId, uploadBy);
        if (fileById.size() == 0) {
            log.error("No File Available for userId: " + fileUserId);
            throw new ApiException(MessageConstants.NO_FILE_FOUND);
        }
        log.info("File Found: " + fileById);
        return fileById.stream().findFirst().get();
    }

    @Override
    public FileBean updateFileHandler(FileBean fileBean) {
        log.info("Updating File Details...");
        if (fileBean.getFile() != null) {
            fileBean = fileHelper.uploadFileHelper(fileBean.getFile(), fileBean.getFileUserId());
        }
        log.info("Updating Files details to DB.");
        fileRepository.updateFile(fileBean);
        log.info("File Updated Successfully." + fileBean);
        return fileBean;
    }

    @Override
    public int deleteFileHandler(Long fileUserId, Long fileId, String uploadBy) {
        log.info("Deleting File: " + fileId);
        int i = fileRepository.deleteFile(fileUserId, fileId, uploadBy);
        if (i < 0) {
            log.error(String.format("File doesn't exist. [FileId: %s, UserId: %s", fileId, fileUserId));
            throw new ApiException(MessageConstants.NO_FILE_FOUND);
        }
        log.info(i + " File(s) deleted.");
        return i;
    }

    @Override
    public FileBean updateFileStatusHandler(FileBean fileBean) {
        int status;
        log.info("Updating File Status for: " + fileBean.getFileName());
        if (Constants.ADMINS.contains(fileBean.getUploadBy())) {
            status = fileRepository.updateFileStatus(fileBean);
        } else {
            log.error("Unauthorized User trying to modify the Author's File status. [UserId: " + fileBean.getFileUserId() + "]");
            throw new ApiException(MessageConstants.NOT_AUTHORIZED_FILE_STATUS);
        }
        if (status > 0) {
            log.info("Status has been updated for file: " + fileBean.getFileName());
            return fileBean;
        } else throw new ApiException(MessageConstants.NO_FILE_FOUND);
    }

    @Override
    public List<FileBean> findReportsForAuthorFile(long authorId, long fileId) {
        log.info(String.format("Fetching All (Pre)-Editior's Report for [AuthorId:%s, fileId:%s", authorId, fileId));
        List<FileBean> reportForAuthorFileById = fileRepository.findReportsForAuthorFileById(authorId, fileId);
        if (reportForAuthorFileById.size() == 0) {
            log.error(String.format("No Report Available for [AuthorId:%s, fileId:%s", authorId, fileId));
            throw new ApiException(MessageConstants.NO_FILE_FOUND);
        }
        log.info(reportForAuthorFileById.size() + " Reports Found.");
        log.info(reportForAuthorFileById.stream().findFirst().get().getUserName());
        return reportForAuthorFileById;
    }

    @Override
    public List<FileBean> findAllReportsForAuthor(long authorId) {
        log.info("Fetching All Reports for the Author: " + authorId);
        List<FileBean> allReportsByAuthorId = fileRepository.findAllReportsByAuthorId(authorId);
        if (allReportsByAuthorId.size() == 0) {
            log.error("No Reports Available for the author: " + authorId);
            throw new ApiException(MessageConstants.NO_FILE_FOUND);
        }
        log.info(allReportsByAuthorId.size() + " Reports Found");
        return allReportsByAuthorId;
    }

    @Override
    public List<FileBean> fetchAllFilesByUser(String role) {
        log.info("Fetching All Files for All Authors...");
        List<FileBean> allAuthorsFiles = fileRepository.getAllFilesByUser(role);
        if (allAuthorsFiles.size() == 0) {
            log.error("No File Available Yet.");
            throw new ApiException(MessageConstants.NO_FILE_FOUND);
        }
        log.info(allAuthorsFiles.size() + " Files Found.");
        return allAuthorsFiles;
    }

    @Override
    public FileBean findFileByIdAndRole(long authorId, long fileId, String role) {
        log.info(String.format("Fetching [AuthorId: %s, FileId: %s", authorId, fileId));
        List<FileBean> authorFileById = fileRepository.fetchFileInfoByIdAndRole(authorId, fileId, role);
        if (authorFileById.size() == 0) {
            log.error("No File Available yet.");
            throw new ApiException(MessageConstants.NO_FILE_FOUND);
        }
        log.info(authorFileById.size() + " File(s) found.");
        return authorFileById.stream().findFirst().get();
    }

    @Override
    public FileBean downloadFileHandler(long fileUserId, long fileId, long reportId, String uploadBy, String isReport) {
        log.info("Fetching File Details from DB...");
        FileBean fileBean;
        if ("Y".equalsIgnoreCase(isReport))
            fileBean = findReportForAuthorFile(fileUserId, fileId, reportId);
        else
            fileBean = findFileByIdHandler(fileUserId, fileId, uploadBy);
        log.info("File Found from db: " + fileBean.getFileName());
        Resource resource = fileHelper.loadFileAsResourceHelper(fileBean.getPath());
        fileBean.setResource(resource);
        log.info("Resource updated in fileBean.");
        return fileBean;
    }

    @Override
    public FileBean findReportForAuthorFile(long authorId, long fileId, long reportId) {
        log.info(String.format("Fetching (Pre)-Editor's Report for [AuthorId:%s, fileId:%s", authorId, fileId));
        List<FileBean> reportForAuthorFileById = fileRepository.findReportForAuthorFileById(authorId, fileId, reportId);
        if (reportForAuthorFileById.size() == 0) {
            log.error(String.format("No Report Available for [AuthorId:%s, fileId:%s", authorId, fileId));
            throw new ApiException(MessageConstants.NO_FILE_FOUND);
        }
        log.info(reportForAuthorFileById.size() + " Reports Found.");
        return reportForAuthorFileById.stream().findFirst().get();
    }
}
