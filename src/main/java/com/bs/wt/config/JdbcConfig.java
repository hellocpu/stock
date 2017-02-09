package com.bs.wt.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * ʹ��  @PropertySource ���������ļ�
 * ��һ�ַ��� ��� ProConfig.java
 * @author bangsun
 *
 */
@Component
@PropertySource("classpath:jdbc.properties")
public class JdbcConfig {

}
