package com.tzs.marshall.filesystem;

import com.tzs.marshall.constants.Constants;
import com.tzs.marshall.constants.MessageConstants;
import com.tzs.marshall.error.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class FileRepositoryImpl implements FileRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(FileRepositoryImpl.class);
    private static final String REPORT_FLAG_AND_ORDER = " AND cr.is_deleted=:isDeleted ORDER BY cr.modify_date DESC";
    private static final String FILE_FLAG_AND_ORDER = " AND c.is_deleted=:isDeleted ORDER BY c.modify_date DESC";

    public FileRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int saveFile(FileBean fileBean) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            if (Constants.USER.equalsIgnoreCase(fileBean.getUploadBy())) {
                mapSqlParameterSource.addValue("fileUserId", fileBean.getFileUserId())
                        .addValue("fileName", fileBean.getFileName())
                        .addValue("fileFormat", fileBean.getFileFormat())
                        .addValue("topic", fileBean.getTopic())
                        .addValue("category", fileBean.getCategory())
                        .addValue("description", fileBean.getDescription())
                        .addValue("language", fileBean.getLanguage())
                        .addValue("wordsCount", fileBean.getWordsCount())
                        .addValue("numberOfFigures", fileBean.getNumberOfFigures())
                        .addValue("requestedServices", fileBean.getRequestedServices())
                        .addValue("fileStatus", fileBean.getFileStatus())
                        .addValue("path", fileBean.getPath())
                        .addValue("size", fileBean.getSize())
                        .addValue("upload_date", fileBean.getUploadDate())
                        .addValue("requestServeDate", fileBean.getRequestServeDate())
                        .addValue("modifyDate", fileBean.getModifyDate());

                return new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getJdbcTemplate().getDataSource()))
                        .withTableName("content")
                        .usingColumns("file_user_id", "file_name", "file_format", "topic", "category",
                                "description", "language", "words_count", "number_of_figures", "requested_services", "file_status",
                                "path", "size", "upload_date", "request_serve_date", "modify_date")
                        .execute(mapSqlParameterSource);
            } else {
                mapSqlParameterSource.addValue("fileUserId", fileBean.getFileUserId())
                        .addValue("authorFileId", fileBean.getAuthorFileId())
                        .addValue("authorUserId", fileBean.getAuthorUserId())
                        .addValue("fileName", fileBean.getFileName())
                        .addValue("fileFormat", fileBean.getFileFormat())
                        .addValue("reportType", fileBean.getReportType())
                        .addValue("topic", fileBean.getTopic())
                        .addValue("category", fileBean.getCategory())
                        .addValue("description", fileBean.getDescription())
                        .addValue("language", fileBean.getLanguage())
                        .addValue("proposedServices", fileBean.getProposedServices())
                        .addValue("path", fileBean.getPath())
                        .addValue("size", fileBean.getSize())
                        .addValue("upload_date", fileBean.getUploadDate())
                        .addValue("modifyDate", fileBean.getModifyDate());

                return new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getJdbcTemplate().getDataSource()))
                        .withTableName("content_report")
                        .usingColumns("file_user_id", "author_file_id", "author_user_id", "file_name", "file_format", "report_type",
                                "topic", "category", "description", "language", "proposed_services",
                                "path", "size", "upload_date", "modify_date")
                        .execute(mapSqlParameterSource);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<FileBean> findFiles(Long fileUserId, String uploadBy) {
        String query;
        if (Constants.USER.equalsIgnoreCase(uploadBy)) {
            query = "SELECT * FROM marshall_service.content WHERE file_user_id=:fileUserId AND is_deleted=:is_deleted ORDER BY modify_date DESC";
        } else {
            query = "SELECT * FROM marshall_service.content_report WHERE file_user_id=:fileUserId AND is_deleted=:is_deleted ORDER BY modify_date DESC";
        }
        try {
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("fileUserId", fileUserId).addValue("is_deleted", Constants.isDeleted),
                    BeanPropertyRowMapper.newInstance(FileBean.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<FileBean> findFileById(Long fileUserId, Long fileId, String uploadBy) {
        String query;
        if (Constants.USER.equalsIgnoreCase(uploadBy)) {
            query = "SELECT * FROM marshall_service.content WHERE author_file_id=:fileId AND is_deleted=:is_deleted ORDER BY modify_date DESC";
        } else {
            query = "SELECT * FROM marshall_service.content_report WHERE admin_file_id=:fileId AND file_user_id=:fileUserId AND is_deleted=:is_deleted ORDER BY modify_date DESC";
        }
        try {
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("fileId", fileId)
                            .addValue("fileUserId", fileUserId).addValue("is_deleted", Constants.isDeleted),
                    BeanPropertyRowMapper.newInstance(FileBean.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public int updateFile(FileBean fileBean) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            if (Constants.USER.equalsIgnoreCase(fileBean.getUploadBy())) {
                String authorQuery = "UPDATE marshall_service.content SET " +
                        "file_name=:fileName, file_format=:fileFormat, topic=:topic, category=:category, description=:description," +
                        " language=:language, words_count=:wordsCount, number_of_figures=:numberOfFigures, requested_services=:requestedServices," +
                        " path=:path, size=:size, request_serve_date=:requestServeDate, modify_date=:modifyDate" +
                        " WHERE author_file_id=:authorFileId AND file_user_id=:fileUserId AND is_deleted=:isDeleted";

                mapSqlParameterSource.addValue("authorFileId", fileBean.getAuthorFileId())
                        .addValue("fileUserId", fileBean.getFileUserId())
                        .addValue("fileName", fileBean.getFileName())
                        .addValue("fileFormat", fileBean.getFileFormat())
                        .addValue("topic", fileBean.getTopic())
                        .addValue("category", fileBean.getCategory())
                        .addValue("description", fileBean.getDescription())
                        .addValue("language", fileBean.getLanguage())
                        .addValue("wordsCount", fileBean.getWordsCount())
                        .addValue("numberOfFigures", fileBean.getNumberOfFigures())
                        .addValue("requestedServices", fileBean.getRequestedServices())
                        .addValue("path", fileBean.getPath())
                        .addValue("size", fileBean.getSize())
                        .addValue("requestServeDate", fileBean.getRequestServeDate())
                        .addValue("modifyDate", Timestamp.valueOf(LocalDateTime.now()))
                        .addValue("isDeleted", Constants.isDeleted);

                return jdbcTemplate.update(authorQuery, mapSqlParameterSource);
            } else {
                String adminQuery = "UPDATE marshall_service.content_report SET " +
                        "file_name=:fileName, file_format=:fileFormat, report_type=:reportType, topic=:topic, category=:category, description=:description," +
                        " language=:language, proposed_services=:proposedServices," +
                        " path=:path, size=:size, modify_date=:modifyDate" +
                        " WHERE admin_file_id=:adminFileId AND file_user_id=:fileUserId AND author_file_id=:authorFileId " +
                        " AND author_user_id=:authorUserId AND is_deleted=:isDeleted";

                mapSqlParameterSource.addValue("adminFileId", fileBean.getAdminFileId())
                        .addValue("fileUserId", fileBean.getFileUserId())
                        .addValue("authorFileId", fileBean.getAuthorFileId())
                        .addValue("authorUserId", fileBean.getAuthorUserId())
                        .addValue("fileName", fileBean.getFileName())
                        .addValue("fileFormat", fileBean.getFileFormat())
                        .addValue("reportType", fileBean.getReportType())
                        .addValue("topic", fileBean.getTopic())
                        .addValue("category", fileBean.getCategory())
                        .addValue("description", fileBean.getDescription())
                        .addValue("language", fileBean.getLanguage())
                        .addValue("proposedServices", fileBean.getProposedServices())
                        .addValue("path", fileBean.getPath())
                        .addValue("size", fileBean.getSize())
                        .addValue("modifyDate", Timestamp.valueOf(LocalDateTime.now()))
                        .addValue("isDeleted", Constants.isDeleted);

                return jdbcTemplate.update(adminQuery, mapSqlParameterSource);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public int deleteFile(Long fileUserId, Long fileId, String uploadBy) {
        try {
            String query;
            if (Constants.USER.equalsIgnoreCase(uploadBy)) {
                query = "UPDATE marshall_service.content SET " +
                        "is_deleted=:is_deleted, modify_date=:modifyDate " +
                        "WHERE author_file_id=:fileId AND file_user_id=:fileUserId";
            } else {
                query = "UPDATE marshall_service.content_report SET " +
                        "is_deleted=:is_deleted, modify_date=:modifyDate " +
                        "WHERE admin_file_id=:fileId AND file_user_id=:fileUserId";
            }
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

            mapSqlParameterSource.addValue("fileId", fileId)
                    .addValue("fileUserId", fileUserId)
                    .addValue("is_deleted", !Constants.isDeleted)
                    .addValue("modifyDate", Timestamp.valueOf(LocalDateTime.now()));

            return jdbcTemplate.update(query, mapSqlParameterSource);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public int updateFileStatus(FileBean fileBean) {
        try {
            String authorQuery = "UPDATE marshall_service.content SET " +
                    "file_status=:fileStatus, modify_date=:modifyDate" +
                    " WHERE author_file_id=:authorFileId AND file_user_id=:fileUserId AND is_deleted=:isDeleted";

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("authorFileId", fileBean.getAuthorFileId())
                    .addValue("fileUserId", fileBean.getFileUserId())
                    .addValue("fileStatus", fileBean.getFileStatus())
                    .addValue("isDeleted", Constants.isDeleted)
                    .addValue("modifyDate", Timestamp.valueOf(LocalDateTime.now()));

            return jdbcTemplate.update(authorQuery, mapSqlParameterSource);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<FileBean> findReportsForAuthorFileById(long authorId, long fileId) {
        try {
            String query = "SELECT ur.user_name, cr.* FROM marshall_service.content_report cr, marshall_service.user_registration ur " +
                    "WHERE cr.file_user_id=ur.user_id AND cr.author_file_id=:fileId AND cr.author_user_id=:authorId" + REPORT_FLAG_AND_ORDER;
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("fileId", fileId)
                            .addValue("authorId", authorId)
                            .addValue("isDeleted", Constants.isDeleted),
                    BeanPropertyRowMapper.newInstance(FileBean.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<FileBean> findAllReportsByAuthorId(long authorId) {
        try {
            String query = "SELECT * FROM marshall_service.content_report WHERE author_user_id=:authorId ORDER BY modify_date DESC";
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("authorId", authorId),
                    BeanPropertyRowMapper.newInstance(FileBean.class));
        }  catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<FileBean> getAllFilesByUser(String role) {
        String query;
        try {
            if (Constants.USER.equalsIgnoreCase(role)) {
                query = "SELECT * FROM marshall_service.content WHERE is_deleted=:isDeleted ORDER BY modify_date DESC";
            } else {
                query = "SELECT * FROM marshall_service.content_report cr, marshall_service.view_user_rights ur where cr.file_user_id=ur.user_id and ur.role_name=:role AND cr.is_deleted=:isDeleted ORDER BY cr.modify_date DESC";

            }
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("role", role)
                            .addValue("isDeleted", Constants.isDeleted),
                    BeanPropertyRowMapper.newInstance(FileBean.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<FileBean> fetchFileInfoByIdAndRole(long authorId, long fileId, String role) {
        String query;
        try {
            if (Constants.USER.equalsIgnoreCase(role) || role == null || role.equalsIgnoreCase("null")) {
                query = "SELECT * FROM marshall_service.content WHERE author_file_id=:fileId AND file_user_id=:authorId AND is_deleted=:isDeleted " +
                        "ORDER BY modify_date DESC";
            } else {
                query = "SELECT * FROM marshall_service.content_report WHERE (admin_file_id=:fileId OR author_file_id=:fileId) AND file_user_id=:authorId AND is_deleted=:isDeleted " +
                        "ORDER BY modify_date DESC";
            }

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("authorId", authorId)
                    .addValue("fileId", fileId)
                    .addValue("isDeleted", Constants.isDeleted);
            return jdbcTemplate.query(query, mapSqlParameterSource, BeanPropertyRowMapper.newInstance(FileBean.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<FileBean> findReportForAuthorFileById(long authorId, long fileId, long reportId) {
        try {
            String query = "SELECT * FROM marshall_service.content_report WHERE author_file_id=:fileId AND " +
                    "(author_user_id=:authorId OR file_user_id=:authorId) " +
                    "AND admin_file_id=:reportId ORDER BY modify_date DESC";
            return jdbcTemplate.query(query, new MapSqlParameterSource().addValue("fileId", fileId)
                            .addValue("authorId", authorId).addValue("reportId", reportId),
                    BeanPropertyRowMapper.newInstance(FileBean.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<FileBean> getAllFilesAssignedToEditor(Long editorId) {
        try {
            String select = "SELECT c.* ";
            String where = " AND cr.file_user_id = :editorId ";
            String query = select + Constants.EDITOR_QUERY + where + FILE_FLAG_AND_ORDER;
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("editorId", editorId)
                            .addValue("isDeleted", Constants.isDeleted),
                    BeanPropertyRowMapper.newInstance(FileBean.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<FileBean> getAuthorFilesAssignedToEditor(Long editorId, long authorId) {
        try {
            String select = "SELECT c.* ";
            String where = " AND cr.file_user_id = :editorId AND ur.user_id = :authorId ";
            String query = select + Constants.EDITOR_QUERY + where + FILE_FLAG_AND_ORDER;
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("editorId", editorId)
                            .addValue("authorId", authorId)
                            .addValue("isDeleted", Constants.isDeleted),
                    BeanPropertyRowMapper.newInstance(FileBean.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<FileBean> findAuthorFileByIdAssignedToEditor(Long editorId, long authorId, long fileId) {
        try {
            String select = "SELECT c.* ";
            String where = " AND cr.file_user_id = :editorId AND ur.user_id = :authorId AND c.author_file_id = :fileId ";
            String query = select + Constants.EDITOR_QUERY + where + FILE_FLAG_AND_ORDER;
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("editorId", editorId)
                            .addValue("authorId", authorId)
                            .addValue("fileId", fileId)
                            .addValue("isDeleted", Constants.isDeleted),
                    BeanPropertyRowMapper.newInstance(FileBean.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<FileBean> findAllReportsForAuthorFileAssignedToEditor(Long editorId, long authorId, long fileId) {
        try {
            String select = "SELECT cr.* ";
            String where = " AND cr.file_user_id = :editorId AND ur.user_id = :authorId AND c.author_file_id = :fileId ";
            String query = select + Constants.EDITOR_QUERY + where + REPORT_FLAG_AND_ORDER;
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("editorId", editorId)
                            .addValue("authorId", authorId)
                            .addValue("fileId", fileId)
                            .addValue("isDeleted", Constants.isDeleted),
                    BeanPropertyRowMapper.newInstance(FileBean.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<FileBean> findReportForAuthorFileAssignedToEditor(Long editorId, long authorId, long fileId, long reportId) {
        try {
            String select = "SELECT cr.* ";
            String where = " AND cr.file_user_id = :editorId AND ur.user_id = :authorId " +
                    "AND c.author_file_id = :fileId AND cr.admin_file_id = :reportId ";
            String query = select + Constants.EDITOR_QUERY + where + REPORT_FLAG_AND_ORDER;
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("editorId", editorId)
                            .addValue("authorId", authorId)
                            .addValue("fileId", fileId)
                            .addValue("reportId", reportId)
                            .addValue("isDeleted", Constants.isDeleted),
                    BeanPropertyRowMapper.newInstance(FileBean.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public List<FileBean> findAllReportsByAuthorIdAssignedToEdito(Long editorId, long authorId) {
        try {
            String select = "SELECT cr.* ";
            String where = " AND cr.file_user_id = :editorId AND ur.user_id = :authorId ";
            String query = select + Constants.EDITOR_QUERY + where + REPORT_FLAG_AND_ORDER;
            return jdbcTemplate.query(query, new MapSqlParameterSource()
                            .addValue("editorId", editorId)
                            .addValue("authorId", authorId)
                            .addValue("isDeleted", Constants.isDeleted),
                    BeanPropertyRowMapper.newInstance(FileBean.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }
}
