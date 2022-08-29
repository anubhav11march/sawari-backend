package com.tzs.marshall.filesystem;

import java.util.List;

public interface FileRepository {

    int saveFile(FileBean fileBean);

    List<FileBean> findFiles(Long fileUserId, String uploadBy);

    List<FileBean> findFileById(Long fileUserId, Long fileId, String uploadBy);

    int updateFile(FileBean fileBean);

    int deleteFile(Long fileUserId, Long fileId, String uploadBy);

    int updateFileStatus(FileBean fileBean);

    List<FileBean> findReportsForAuthorFileById(long authorId, long fileId);

    List<FileBean> findAllReportsByAuthorId(long authorId);

    List<FileBean> getAllFilesByUser(String role);

    List<FileBean> fetchFileInfoByIdAndRole(long authorId, long fileId, String role);

    List<FileBean> findReportForAuthorFileById(long authorId, long fileId, long reportId);

    List<FileBean> getAllFilesAssignedToEditor(Long editorId);

    List<FileBean> getAuthorFilesAssignedToEditor(Long editorId, long authorId);

    List<FileBean> findAuthorFileByIdAssignedToEditor(Long editorId, long authorId, long fileId);

    List<FileBean> findAllReportsForAuthorFileAssignedToEditor(Long editorId, long authorId, long fileId);

    List<FileBean> findReportForAuthorFileAssignedToEditor(Long editorId, long authorId, long fileId, long reportId);

    List<FileBean> findAllReportsByAuthorIdAssignedToEdito(Long editorId, long authorId);
}
