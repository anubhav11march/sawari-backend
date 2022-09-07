package com.tzs.marshall.filesystem;

import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditorFileServiceImpl implements EditorFileService {

    @Autowired
    private FileRepository fileRepository;

    private static final Logger log = LoggerFactory.getLogger(EditorFileServiceImpl.class);

    @Override
    public List<FileBean> fetchAllFilesAssignedToEditor(Long editorId) {
        log.info("Fetching all files assigned to Editor: " + editorId);
        List<FileBean> allAuthorsFiles = fileRepository.getAllFilesAssignedToEditor(editorId);
        validate(allAuthorsFiles, "No File Available Yet.");
        log.info(allAuthorsFiles.size() + " Files Found.");
        return allAuthorsFiles;
    }

    @Override
    public List<FileBean> findAuthorFilesAssignedToEditor(Long editorId, long authorId) {
        log.info("Fetching Author's all files assigned to Editor: " + editorId);
        List<FileBean> authorAllFiles = fileRepository.getAuthorFilesAssignedToEditor(editorId, authorId);
        validate(authorAllFiles, "No File Available Yet.");
        log.info(authorAllFiles.size() + " Files Found.");
        return authorAllFiles;
    }

    @Override
    public FileBean findAuthorFileByIdAssignedToEditor(Long editorId, long authorId, long fileId) {
        log.info(String.format("Fetching file assigned to editor by [AuthorId: %s, FileId: %s", authorId, fileId));
        List<FileBean> authorFileById = fileRepository.findAuthorFileByIdAssignedToEditor(editorId, authorId, fileId);
        validate(authorFileById, "No File Available yet.");
        log.info(authorFileById.size() + " File(s) found.");
        return authorFileById.stream().findFirst().get();
    }

    @Override
    public List<FileBean> findAllReportsForAuthorFileAssignedToEditor(Long editorId, long authorId, long fileId) {
        log.info(String.format("Fetching All Reports assigned to editor by [AuthorId:%s, fileId:%s", authorId, fileId));
        List<FileBean> reportForAuthorFileById = fileRepository.findAllReportsForAuthorFileAssignedToEditor(editorId, authorId, fileId);
        validate(reportForAuthorFileById, String.format("No Report Available for [AuthorId:%s, fileId:%s", authorId, fileId));
        log.info(reportForAuthorFileById.size() + " Reports Found.");
        log.info(reportForAuthorFileById.stream().findFirst().get().getUserName());
        return reportForAuthorFileById;
    }

    @Override
    public FileBean findReportForAuthorFileAssignedToEditor(Long editorId, long authorId, long fileId, long reportId) {
        log.info(String.format("Fetching Report assigned to editor by [AuthorId:%s, fileId:%s", authorId, fileId));
        List<FileBean> reportForAuthorFileById = fileRepository.findReportForAuthorFileAssignedToEditor(editorId, authorId, fileId, reportId);
        validate(reportForAuthorFileById, String.format("No Report Available for [AuthorId:%s, fileId:%s", authorId, fileId));
        log.info(reportForAuthorFileById.size() + " Reports Found.");
        return reportForAuthorFileById.stream().findFirst().get();
    }

    @Override
    public List<FileBean> findAllReportsForAuthorAssignedToEditor(Long editorId, long authorId) {
        log.info("Fetching All Reports assigned to editor for the Author: " + authorId);
        List<FileBean> allReportsByAuthorId = fileRepository.findAllReportsByAuthorIdAssignedToEdito(editorId, authorId);
        validate(allReportsByAuthorId, "No Reports Available for the author: " + authorId);
        log.info(allReportsByAuthorId.size() + " Reports Found");
        return allReportsByAuthorId;
    }

    private void validate(List<FileBean> allAuthorsFiles, String s) {
        if (allAuthorsFiles.size() == 0) {
            log.error(s);
            throw new ApiException(MessageConstants.NO_FILE_FOUND);
        }
    }
}
