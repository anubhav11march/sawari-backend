package com.tzs.marshall;

import com.tzs.marshall.author.bean.AESHProperties;
import com.tzs.marshall.author.constants.Constants;
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
        new AESHProperties(getAESHProperties());
        Constants.ORDER_STATUS.put(Constants.INITIATED, 1);
        Constants.ORDER_STATUS.put(Constants.CREATED, 2);
        Constants.ORDER_STATUS.put(Constants.PENDING, 3);
        Constants.ORDER_STATUS.put(Constants.PLACED, 4);
        Constants.ORDER_STATUS.put(Constants.PAID, 5);
        Constants.ORDER_STATUS.put(Constants.APPROVED, 6);
        Constants.ORDER_STATUS.put(Constants.REJECTED, 7);
        Constants.ORDER_STATUS.put(Constants.CANCELLED, 8);
        Constants.ORDER_STATUS.put(Constants.EXPIRED, 9);
    }

    private Properties getAESHProperties() {
        String query = "Select name, value from ether_service.properties";
        jdbcTemplate.query(query, (rs, rowNum) -> properties.put(rs.getString("name"), rs.getString("value")));
        log.info("Properties Fetched: "+properties);
        return properties;
    }
}
