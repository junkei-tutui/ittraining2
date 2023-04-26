package jp.zein.it.training.common.service.storage;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;

/**
 * ファイル・ストレージ・サービス。
 *
 * <p>
 * AWS S3上のファイルを操作するサービス
 * </p>
 */
public interface TrainingFileStorageService {

	/**
	 * ファイル種別
	 */
	enum FileType {
		/**
		 * ファイル
		 */
		NOMAL("nomal");

		private final String value;

		private FileType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * ファイル種別およびファイルパスで指定したファイルの{@link InputStream}を取得
	 *
	 * @param fileType {@link FileType}
	 * @param filePath ファイルパス
	 * @return ファイルの{@link Resource}
	 * @throws java.io.FileNotFoundException ファイルが存在しない場合
	 * @throws IOException                   ファイルが存在しないなど、ファイル取得失敗
	 */
	Resource load(FileType fileType, String filePath) throws IOException;

	/**
	 * {@link InputStream}の内容をファイル種別およびファイルパスで指定したファイルに保存
	 *
	 * <p>
	 * すでにファイルが存在する場合は上書き
	 * </p>
	 *
	 * @param fileType {@link FileType}
	 * @param filePath ファイルパス
	 * @param is       {@link InputStream}
	 * @return 保存したファイルパス
	 * @throws IOException ファイル出力失敗
	 */
	String save(FileType fileType, String filePath, InputStream is) throws IOException;
}
