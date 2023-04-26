package jp.zein.it.training.was.controller.b402.stockfileupload;

import java.io.IOException;

import javax.validation.constraints.NotEmpty;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jp.zein.it.training.common.generate.entity.TblStockCaptureEntity;
import jp.zein.it.training.common.service.TrainingDateTimeService;
import jp.zein.it.training.common.util.TrainingConsts;
import jp.zein.it.training.was.dao.WasTblStockCaptureEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import jp.zein.it.training.was.service.stock.StockManagementService;
import jp.zein.it.training.was.util.WasDownloadUtils;
import jp.zein.it.training.was.util.csv.TextFileUtils;
import lombok.RequiredArgsConstructor;

/**
 * B402:在庫データ取込コントローラー
 */
@RestController
@RequiredArgsConstructor
public class B402Controller {

	private final TrainingDateTimeService dateTimeService;

	private final StockManagementService stockManagementService;

	private final WasTblStockCaptureEntityDao tblStockCaptureEntityDao;

	/**
	 * B402:在庫データ取込
	 * 
	 * @param stockCsvFile アップロードされた在庫データCSVファイル
	 * @param fileName     在庫データCSVファイルのファイル名
	 * @return 取込エラー時のエラーCSVファイル（{@link ResponseEntity<byte[]>}）、正常終了時は{@code null}
	 * @throws IllegalArgumentException CSVのバリデーションチェックにより発生する例外
	 * @throws IllegalAccessException   CSVバリデーションチェックの重複チェックにてフィールド参照エラーにより発生する例外
	 * @throws IOException              ImputStream参照エラー等により発生する例外
	 */
	@PostMapping("/stock/upload")
	@Transactional
	public ResponseEntity<byte[]> upload(@RequestParam(name = "uploadFile", required = true) MultipartFile stockCsvFile,
			@RequestParam(name = "fileName", required = true) @NotEmpty String fileName)
			throws IllegalArgumentException, IllegalAccessException, IOException {

		// 在庫取込ファイルの取込重複チェック
		if (tblStockCaptureEntityDao.isDuplicatedByFileNameStock(fileName)) {
			throw new TrainingApplicationException("アップロードされた在庫ファイルは既に取り込んでいます。");
		}

		// 在庫取込処理を実施
		// 1) リソース（CSVファイル）の取得
		var hasError = stockManagementService.importStockCsv(stockCsvFile.getInputStream());

		// 1件以上エラーがあった場合は取得したレスポンスデータを返却する。（処理終了）
		if (hasError) {
			// 元のファイル名よりエラーファイル名を生成する。 ※ 必ず拡張子があることを前提とする。
			String[] fileNameSplit = fileName.split("\\.(?=[^\\.]+$)");

			// エラーファイル名を生成する。
			var filename = String.format("%s%s_%s.%s", TrainingConsts.ERROR_FILE_PRIFIX, fileNameSplit[0],
					dateTimeService.now().format(TrainingConsts.FILE_DATETIME_FORMETTER), fileNameSplit[1]);

			return WasDownloadUtils.responseBinary(WasDownloadUtils.MIME_CSV, filename,
					TextFileUtils.toBinaryWithBom(stockManagementService.getTextWithValidationErrorMessage()));
		}

		// 在庫引当処理を実施
		// 4) 引当対象の受注を検索する
		// 5) 引当対象の受注明細を検索する
		stockManagementService.allocationStock();

		// 引当後在庫ファイルを出力する
		// 6) 引当後在庫ファイル名を決定する。
		// 7) 引当後在庫ファイルを作成する。
		var stockResultFileName = stockManagementService.outputStockResultCsv();

		// 引当不可リストファイルを出力する
		// 8) 引当不可リストファイル名を決定する。
		// 9) 引当不可リストファイルを作成する。
		var cannotReserveFileName = stockManagementService.outputNoStockOrderCsv();

		// 10) 在庫取込管理テーブルを登録する
		var stockCaptureData = new TblStockCaptureEntity();
		stockCaptureData.setStockCaptureId(null); // サロゲートキー
		stockCaptureData.setUploadAt(dateTimeService.now());
		stockCaptureData.setFilenameStock(fileName);
		stockCaptureData.setFilenameStockResult(stockResultFileName);
		stockCaptureData.setFilenameCannotreserve(cannotReserveFileName);
		stockCaptureData.setIsDelete(false);
		tblStockCaptureEntityDao.insert(stockCaptureData);

		// 正常終了時はnull
		return null;
	}
}