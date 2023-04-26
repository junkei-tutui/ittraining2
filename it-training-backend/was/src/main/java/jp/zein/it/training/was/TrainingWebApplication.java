package jp.zein.it.training.was;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import jp.zein.it.training.common.config.AppPropertiesConfig;
import jp.zein.it.training.common.config.CommonConfig;

/**
 * 研修用：注文確認システムアプリケーション
 *
 */
@SpringBootApplication
@Import({ CommonConfig.class, AppPropertiesConfig.class })
public class TrainingWebApplication {
	/**
	 * システムメインクラス
	 */
	public static void main(String[] args) {
		SpringApplication.run(TrainingWebApplication.class, args);
	}
}
