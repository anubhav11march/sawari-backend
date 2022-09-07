package com.tzs.marshall.service.editor;


import com.tzs.marshall.bean.PersistentUserDetails;

import java.util.List;

public interface EditorService {

    List<PersistentUserDetails> getAllAuthorsAssignedToEditor(Long editorId);

    PersistentUserDetails getAuthorDetailsByIdAssignedToEditor(Long editorId, long userId);
}
