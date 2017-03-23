package com.bs.wt.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author bangsun
 *
 */
@Component
@PropertySource("classpath:jdbc.properties")
public class JdbcConfig {

}
