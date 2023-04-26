package jp.zein.it.training.was.controller.b404.stockresultfiledownload;

import java.io.IOException;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.dao.WasTblStockCaptureEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import jp.zein.it.training.was.util.WasDownloadUtils;
import lombok.RequiredArgsConstructor;

/**
 * B404：引当後在庫ファイル取得コントローラー
 */
@RestController
@RequiredArgsConstructor
public class B404Controller {

	private final ResourceLoader resourceLoader;

	private final WasTblStockCaptureEntityDao tblStockCaptureEntityDao;

	/**
	 * 引当結果在庫データの出力先パス
	 */
	@Value("${training.file-storage.path-prefix.output-stock-result}")
	private String stockResultListPath;

	/**
	 * B404：引当後在庫ファイル取得
	 * 
	 * @param stockCaptureId 取得する引当後在庫ファイルの在庫取込管理ID
	 * @return 引当後在庫ファイル（CSVファイル）データ
	 * @throws IOException ImputStream参照エラー等により発生する例外
	 */
	@GetMapping("/stock/result/download")
	public ResponseEntity<org.springframework.core.io.Resource> stockResultDownload(
			@RequestParam(name = "stockCaptureId", required = true) @NotEmpty Long stockCaptureId) throws IOException {

		// 1) 引当後在庫ファイル名を取得する
		// 検索概要：在庫取込管理テーブルより情報を取得する。
		var entity = tblStockCaptureEntityDao.selectById(stockCaptureId);
		if (Objects.isNull(entity)) {
			throw new TrainingApplicationException("対象の在庫取込管理データは削除された可能性があります。管理者にお問い合わせください。");
		}

		// 2) 引当後在庫ファイルを取得する
		var resource = resourceLoader.getResource("file:" + stockResultListPath + entity.getFilenameStockResult());
		if (!resource.exists()) {
			throw new TrainingApplicationException("対象のファイルは削除された可能性があります。管理者にお問い合わせください。");
		}

		// 3) 正常終了で処理を終了する。
		return WasDownloadUtils.responseBinary(MediaType.APPLICATION_OCTET_STREAM_VALUE,
				// 1)で取得した「引当後在庫ファイル」
				entity.getFilenameStockResult(), resource);
	}

}