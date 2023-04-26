package jp.zein.it.training.was.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * ダウンロード・ユーティリティ
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WasDownloadUtils {

	/**
	 * MIME：CSV
	 */
	public static final String MIME_CSV = "text/csv; charset=" + StandardCharsets.UTF_8.name();

	private static final String ATTACHMENT_FORMAT = "attachment; filename*=utf-8''%s";

	/**
	 * バイナリデータをレスポンスする{@link ResponseEntity}を生成
	 *
	 * @param mime           MIME
	 * @param filename       ファイル名(日本語可)
	 * @param downloadBinary ダウンロードするバイナリ
	 * @return {@link ResponseEntity}
	 */
	public static ResponseEntity<byte[]> responseBinary(String mime, String filename, byte[] downloadBinary) {
		return responseBinary(mime, filename, downloadBinary, HttpStatus.OK);
	}

	/**
	 * バイナリデータをレスポンスする{@link ResponseEntity}を生成
	 *
	 * @param mime           MIME
	 * @param filename       ファイル名(日本語可)
	 * @param downloadBinary ダウンロードするバイナリ
	 * @param httpStatus     {@link HttpStatus}
	 * @return {@link ResponseEntity}
	 */
	public static ResponseEntity<byte[]> responseBinary(String mime, String filename, byte[] downloadBinary,
			HttpStatus httpStatus) {
		return new ResponseEntity<>(downloadBinary, createHttpHeader(mime, filename), httpStatus);
	}

	/**
	 * バイナリデータ({@link Resource})をレスポンスする{@link ResponseEntity}を生成
	 *
	 * @param mime     MIME
	 * @param filename ファイル名(日本語可)
	 * @param resource {@link Resource}
	 * @return {@link ResponseEntity}
	 */
	public static ResponseEntity<Resource> responseBinary(String mime, String filename, Resource resource) {
		return new ResponseEntity<>(resource, createHttpHeader(mime, filename), HttpStatus.OK);
	}

	/**
	 * HTTPヘッダー設定を作成
	 *
	 * @param mime
	 * @param filename
	 * @return {@link HttpHeaders}
	 */
	private static HttpHeaders createHttpHeader(String mime, String filename) {
		var responseHeader = new HttpHeaders();
		responseHeader.add(HttpHeaders.CONTENT_TYPE, mime);
		responseHeader.add(HttpHeaders.CONTENT_DISPOSITION,
				String.format(ATTACHMENT_FORMAT, URLEncoder.encode(filename, StandardCharsets.UTF_8)));

		// axiosがHTTPヘッダーからファイル名を取得出来るようにする
		responseHeader.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
		return responseHeader;
	}

}
