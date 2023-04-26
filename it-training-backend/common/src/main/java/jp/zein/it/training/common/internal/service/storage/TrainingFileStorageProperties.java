package jp.zein.it.training.common.internal.service.storage;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "training.file-storage")
@Getter
@Setter
public class TrainingFileStorageProperties {
	/**
	 * ファイル種別ごとのパスのプレフィックス一覧
	 *
	 * <p>
	 * ファイルパスのプレフィックスの最後は「/」が付いている前提
	 * </p>
	 */
	private Map<String, String> pathPrefix;

}
