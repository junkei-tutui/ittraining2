package jp.zein.it.training.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import jp.zein.it.training.common.internal.config.ModelMapperConfig;

@Configuration
@ComponentScan({ "jp.zein.it.training.common.internal" })
@Import(ModelMapperConfig.class)
public class CommonConfig {

}
