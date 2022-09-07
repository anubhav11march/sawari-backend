package com.tzs.marshall.repo.editor;

import com.tzs.marshall.bean.PersistentUserDetails;
import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditorRepositoryImpl implements EditorRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(EditorRepositoryImpl.class);

    public EditorRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PersistentUserDetails> getAllAuthorsAssignedToEditor(Long editorId) {
        try {
            String select = "SELECT distinct(ur.user_id), ur.first_name, ur.middle_name, ur.last_name, ur.phone, ur.mobile, ur.email, ur.alternate_email, ur.role_name, ur.permission_name, ur.is_enable, ur.is_deleted ";
            String where = " AND cr.file_user_id = :editorId";
            String query = select + Constants.EDITOR_QUERY + where;
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("editorId", editorId),
                    BeanPropertyRowMapper.newInstance(PersistentUserDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<PersistentUserDetails> getAuthorDetailsAssignedToEditorById(Long editorId, long userId) {
        try {
            String select = "SELECT distinct(ur.user_id), ur.first_name, ur.middle_name, ur.last_name, ur.phone, ur.mobile, ur.email, ur.alternate_email, ur.role_name, ur.permission_name, ur.is_enable, ur.is_deleted ";
            String where = "AND cr.file_user_id = :editorId AND ur.user_id = :userId";
            String query = select + Constants.EDITOR_QUERY + where;
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("editorId", editorId)
                            .addValue("userId", userId),
                    BeanPropertyRowMapper.newInstance(PersistentUserDetails.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }
}
