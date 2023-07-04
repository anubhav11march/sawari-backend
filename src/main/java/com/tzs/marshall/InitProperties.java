package com.tzs.marshall;

import com.tzs.marshall.bean.DBProperties;
import com.tzs.marshall.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Properties;

@Configuration
@Order(1)
public class InitProperties {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    public static Properties properties = new Properties();

    private final static Logger log = LoggerFactory.getLogger(InitProperties.class);

    public InitProperties(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        log.info("Fetching Properties from DB");
        Properties dbProperties = getDBProperties();
        new DBProperties(dbProperties);
    }

    public Properties getDBProperties() {
        String query = "Select name, value from marshall_service.properties";
        jdbcTemplate.query(query, (rs, rowNum) -> properties.put(rs.getString("name"), rs.getString("value")));
        log.info("Properties Fetched: "+properties);
        return properties;
    }
}
