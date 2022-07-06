package com.ether.author.token;

import com.ether.author.constants.MessageConstants;
import com.ether.author.error.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class ConfirmationTokenRepositoryImpl implements ConfirmationTokenRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(ConfirmationTokenRepositoryImpl.class);

    public ConfirmationTokenRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int saveToken(ConfirmationToken confirmationToken) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

            mapSqlParameterSource.addValue("token", confirmationToken.getToken());
            mapSqlParameterSource.addValue("reqType", confirmationToken.getReqType());
            mapSqlParameterSource.addValue("email", confirmationToken.getEmail());
            mapSqlParameterSource.addValue("created_at", confirmationToken.getCreatedAt());
            mapSqlParameterSource.addValue("expires_at", confirmationToken.getExpiresAt());
            mapSqlParameterSource.addValue("confirms_at", null);

            String updateQuery = "UPDATE ether_service.confirmation_token SET token=:token, created_at=:created_at, expires_at=:expires_at, confirms_at=:confirms_at" +
                    " WHERE email=:email AND req_type=:reqType";
            int update = jdbcTemplate.update(updateQuery, mapSqlParameterSource);
            if (update == 0) {
                update = new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getJdbcTemplate().getDataSource()))
                        .withTableName("confirmation_token")
                        .usingColumns("token", "req_type", "email", "created_at", "expires_at", "confirms_at")
                        .execute(mapSqlParameterSource);
            }
            return update;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }

    @Override
    public ConfirmationToken findByToken(String token, String reqType) {
        String query = "SELECT * FROM ether_service.confirmation_token WHERE token=:token and req_type=:reqType";
        try {
            List<ConfirmationToken> tokenList = jdbcTemplate.query(query, new MapSqlParameterSource().addValue("token", token).addValue("reqType", reqType),
                    new BeanPropertyRowMapper<>(ConfirmationToken.class));
            if (tokenList.size() != 0)
                return tokenList.stream().findFirst().get();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
        return null;
    }

    @Override
    public int updateConfirmedAt(ConfirmationToken confirmationToken) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

            mapSqlParameterSource.addValue("token", confirmationToken.getToken());
            mapSqlParameterSource.addValue("reqType", confirmationToken.getReqType());
            mapSqlParameterSource.addValue("confirmedAt", confirmationToken.getConfirmsAt());
            String query = "UPDATE ether_service.confirmation_token SET confirms_at=:confirmedAt " +
                    "WHERE token=:token and req_type=:reqType";
            return jdbcTemplate.update(query, mapSqlParameterSource);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(MessageConstants.SOMETHING_WRONG);
        }
    }
}
