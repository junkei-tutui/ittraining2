package jp.zein.it.training.common.internal.service.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import jp.zein.it.training.common.service.exception.TrainingServiceException;
import jp.zein.it.training.common.service.storage.TrainingFileStorageService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TrainingFileStorageServiceImpl implements TrainingFileStorageService {

	/**
	 * {@link ResourceLoader}
	 */
	private final ResourceLoader resourceLoader;

	/**
	 * {@link TrainingFileStorageProperties}
	 */
	private final TrainingFileStorageProperties fileStorageProperties;

	@Override
	public Resource load(FileType fileType, String filePath) throws IOException {
		return resourceLoader.getResource(getPrefix(fileType) + filePath);
	}

	@Override
	public String save(FileType fileType, String filePath, InputStream is) throws IOException {
		var location = getPrefix(fileType) + filePath;
		try (OutputStream os = ((WritableResource) resourceLoader.getResource(location)).getOutputStream()) {
			StreamUtils.copy(is, os);
		}

		return location;
	}

	/**
	 * ファイル種別に該当するファイルパスのプレフィックスを取得
	 *
	 * @param fileType {@link FileType}
	 * @return ファイルパスのプレフィックス
	 */
	private String getPrefix(FileType fileType) {
		return Optional.ofNullable(fileStorageProperties.getPathPrefix().get(fileType.getValue()))
				.orElseThrow(() -> new TrainingServiceException(
						"ファイル種別(%s)に該当するファイルパスのプレフィックス(training.file-storage.path-prefix.%s)が未定義です。",
						new Object[] { fileType.name(), fileType.getValue() }));
	}

}
