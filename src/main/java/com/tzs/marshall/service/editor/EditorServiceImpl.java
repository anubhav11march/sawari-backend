package com.tzs.marshall.service.editor;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import com.tzs.marshall.repo.editor.EditorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditorServiceImpl implements EditorService {

    @Autowired
    private EditorRepository editorRepository;

    private static final Logger log = LoggerFactory.getLogger(EditorServiceImpl.class);

    @Override
    public List<PersistentUserDetails> getAllAuthorsAssignedToEditor(Long editorId) {
        log.info("Fetching All Authors Details assigned to editor: " + editorId);
        List<PersistentUserDetails> allAuthors = editorRepository.getAllAuthorsAssignedToEditor(editorId);
        if (allAuthors.size() == 0) {
            log.error("No User Found...{}", allAuthors);
            throw new ApiException(MessageConstants.NO_AUTHOR_ASSIGNED);
        }
        log.info("Records Found: {}", allAuthors);
        return allAuthors;
    }

    @Override
    public PersistentUserDetails getAuthorDetailsByIdAssignedToEditor(Long editorId, long userId) {
        log.info("Fetching All Authors Details assigned to editor: " + editorId);
        List<PersistentUserDetails> authorDetails = editorRepository.getAuthorDetailsAssignedToEditorById(editorId, userId);
        if (authorDetails.size() == 0) {
            log.error("No User Found...{}", authorDetails);
            throw new ApiException(MessageConstants.NO_AUTHOR_ASSIGNED);
        }
        log.info("Records Found: {}", authorDetails);
        return authorDetails.stream().findAny().get();
    }
}
