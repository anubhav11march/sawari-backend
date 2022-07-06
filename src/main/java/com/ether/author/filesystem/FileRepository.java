package com.ether.author.filesystem;

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

    List<FileBean> getAllAuthorsFiles();

    List<FileBean> findAuthorFileById(long authorId, long fileId);

    List<FileBean> findReportForAuthorFileById(long authorId, long fileId, long reportId);
}
