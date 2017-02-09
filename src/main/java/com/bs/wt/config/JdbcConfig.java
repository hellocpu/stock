package com.bs.wt.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 使用  @PropertySource 加载属性文件
 * 另一种方法 详见 ProConfig.java
 * @author bangsun
 *
 */
@Component
@PropertySource("classpath:jdbc.properties")
public class JdbcConfig {

}
