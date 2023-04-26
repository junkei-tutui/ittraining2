package jp.zein.it.training.was.controller.b301.productitemlist;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.dao.WasMstItemEntityDao;
import jp.zein.it.training.was.dao.WasTblOrderDetailEntityDao;
import lombok.RequiredArgsConstructor;

/**
 * B301:商品一覧取得コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class B301Controller {

	private final ModelMapper modelMapper;

	private final WasMstItemEntityDao mstItemEntityDao;

	private final WasTblOrderDetailEntityDao tblOrderDetailEntityDao;

	/**
	 * B301:商品一覧取得
	 * 
	 * @param targetClassificationCode 検索条件となる分類コード
	 * @param likeItemName             検索条件となる商品名（あいまい検索）
	 * @return 検索条件に該当する商品マスタ（{@link B301ResponseRow}））のリスト
	 */
	@GetMapping("/list")
	@Transactional
	public List<B301ResponseRow> getList(
			@RequestParam(name = "targetClassificationCode", required = false) String targetClassificationCode,
			@RequestParam(name = "likeItemName", required = false) String likeItemName) {

		// 商品一覧を取得する
		var list = mstItemEntityDao.searchItemListByConditions(targetClassificationCode, likeItemName).stream()
				.map(v -> {
					var result = new B301ResponseRow();
					modelMapper.map(v, result);

					// 削除可否判定を追加する。
					// 受注データによる利用をチェックする。
					var isOrder = tblOrderDetailEntityDao.isExistsOrderDetailByItem(v.getItemCode());

					// 削除可能フラグを設定する
					result.setIsCanDeleted(!isOrder);

					return result;
				}).collect(Collectors.toList());

		// レスポンス情報にマッピングする
		return list;
	}
}