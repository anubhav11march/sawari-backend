package com.tzs.marshall.filesystem;

import java.util.List;

public interface FileService {

    FileBean uploadFileHandler(FileBean fileBean);

    List<FileBean> findFilesHandler(Long fileUserId, String uploadBy);

    FileBean findFileByIdHandler(Long fileUserId, Long fileId, String uploadBy);

    FileBean updateFileHandler(FileBean fileBean);

    int deleteFileHandler(Long fileUserId, Long fileId, String uploadBy);

    FileBean updateFileStatusHandler(FileBean fileBean);

    List<FileBean> findReportsForAuthorFile(long authorId, long fileId);

    List<FileBean> findAllReportsForAuthor(long authorId);

    List<FileBean> fetchAllFilesByUser(String role);

    FileBean findFileByIdAndRole(long authorId, long fileId, String role);

    FileBean downloadFileHandler(long fileUserId, long fileId, long reportId, String uploadBy, String isReport);

    FileBean findReportForAuthorFile(long authorId, long fileId, long reportId);
}
