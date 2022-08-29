package com.tzs.marshall.repo.editor;


import com.tzs.marshall.bean.PersistentUserDetails;

import java.util.List;

public interface EditorRepository {

    List<PersistentUserDetails> getAllAuthorsAssignedToEditor(Long editorId);

    List<PersistentUserDetails> getAuthorDetailsAssignedToEditorById(Long editorId, long userId);
}
