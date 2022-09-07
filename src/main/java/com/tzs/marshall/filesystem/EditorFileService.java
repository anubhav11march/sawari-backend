package com.tzs.marshall.filesystem;

import java.util.List;

public interface EditorFileService {

    List<FileBean> fetchAllFilesAssignedToEditor(Long editorId);

    List<FileBean> findAuthorFilesAssignedToEditor(Long editorId, long authorId);

    FileBean findAuthorFileByIdAssignedToEditor(Long editorId, long authorId, long fileId);

    List<FileBean> findAllReportsForAuthorFileAssignedToEditor(Long editorId, long authorId, long fileId);

    FileBean findReportForAuthorFileAssignedToEditor(Long editorId, long authorId, long fileId, long reportId);

    List<FileBean> findAllReportsForAuthorAssignedToEditor(Long editorId, long authorId);
}
