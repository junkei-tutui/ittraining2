package jp.zein.it.training.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * app.propertiesのロード
 */
@Configuration
@PropertySource(value = { "classpath:app.properties",
		"classpath:app-${spring.profiles.active}.properties" }, ignoreResourceNotFound = true)
public class AppPropertiesConfig {

}
