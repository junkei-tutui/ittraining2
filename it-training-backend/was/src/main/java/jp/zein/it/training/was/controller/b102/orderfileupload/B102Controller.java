package jp.zein.it.training.was.controller.b102.orderfileupload;

import java.io.IOException;

import javax.validation.constraints.NotEmpty;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jp.zein.it.training.common.service.TrainingDateTimeService;
import jp.zein.it.training.common.util.TrainingConsts;
import jp.zein.it.training.was.service.order.OrderManagementService;
import jp.zein.it.training.was.util.WasDownloadUtils;
import jp.zein.it.training.was.util.csv.TextFileUtils;
import lombok.RequiredArgsConstructor;

/**
 * B102:受注データ取込コントローラー
 */
@RestController
@RequiredArgsConstructor
public class B102Controller {

	private final TrainingDateTimeService dateTimeService;

	private final OrderManagementService orderManagementService;

	/**
	 * B102:受注データ取込
	 * 
	 * @param orderCsvFile アップロードされた受注データCSVファイル
	 * @param fileName     受注データCSVファイルのファイル名
	 * @return 取込エラー時のエラーCSVファイル（{@link ResponseEntity<byte[]>}）、正常終了時は{@code null}
	 * @throws IllegalArgumentException CSVのバリデーションチェックにより発生する例外
	 * @throws IllegalAccessException   CSVバリデーションチェックの重複チェックにてフィールド参照エラーにより発生する例外
	 * @throws IOException              ImputStream参照エラー等により発生する例外
	 */
	@PostMapping("/order/upload")
	@Transactional
	public ResponseEntity<byte[]> upload(@RequestParam(name = "uploadFile", required = true) MultipartFile orderCsvFile,
			@RequestParam(name = "fileName", required = true) @NotEmpty String fileName)
			throws IllegalArgumentException, IllegalAccessException, IOException {

		// 受注登録処理を実施
		var hasError = orderManagementService.importOrderCsv(orderCsvFile.getInputStream());

		// 1件以上エラーがあった場合は取得したレスポンスデータを返却する。（処理終了）
		if (hasError) {
			// 元のファイル名よりエラーファイル名を生成する。 ※ 必ず拡張子があることを前提とする。
			String[] fileNameSplit = fileName.split("\\.(?=[^\\.]+$)");

			// エラーファイル名を生成する。
			var filename = String.format("%s%s_%s.%s", TrainingConsts.ERROR_FILE_PRIFIX, fileNameSplit[0],
					dateTimeService.now().format(TrainingConsts.FILE_DATETIME_FORMETTER), fileNameSplit[1]);

			return WasDownloadUtils.responseBinary(WasDownloadUtils.MIME_CSV, filename,
					TextFileUtils.toBinaryWithBom(orderManagementService.getTextWithValidationErrorMessage()));
		}

		// 正常終了時はnull
		return null;
	}
}