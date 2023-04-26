package jp.zein.it.training.was.controller.b101.orderlist;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.dao.WasTblOrderEntityDao;
import lombok.RequiredArgsConstructor;

/**
 * B101：受注一覧取得コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
public class B101Controller {

	private final ModelMapper modelMapper;

	private final WasTblOrderEntityDao tblOrderEntityDao;

	/**
	 * B101：受注一覧取得
	 * 
	 * @param likeClientName    検索条件となるクライアント名（あいまい検索）
	 * @param targetOrderStatus 検索条件となる注文受付ステータス
	 * @param targetOrderDate   検索条件となる注文日
	 * @return 検索条件に該当する受注データ（{@link B101ResponseRow}））のリスト
	 */
	@GetMapping("/order/list")
	@Transactional
	public List<B101ResponseRow> getList(@RequestParam(name = "likeClientName", required = false) String likeClientName,
			@RequestParam(name = "targetOrderStatus", required = false) String targetOrderStatus,
			@RequestParam(name = "targetOrderDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetOrderDate) {

		// 受注一覧を取得する
		var list = tblOrderEntityDao.searchOrderListByConditions(likeClientName, targetOrderStatus, targetOrderDate);

		// レスポンス情報にマッピングする
		return modelMapper.map(list, new TypeToken<List<B101ResponseRow>>() {
		}.getType());

	}
}