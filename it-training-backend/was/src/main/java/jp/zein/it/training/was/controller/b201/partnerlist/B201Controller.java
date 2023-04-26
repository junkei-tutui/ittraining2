package jp.zein.it.training.was.controller.b201.partnerlist;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.dao.WasMstItemEntityDao;
import jp.zein.it.training.was.dao.WasMstPartnerEntityDao;
import jp.zein.it.training.was.dao.WasTblOrderEntityDao;
import lombok.RequiredArgsConstructor;

/**
 * B201:取引先検索一覧取得コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
public class B201Controller {

	private final ModelMapper modelMapper;

	private final WasMstPartnerEntityDao mstPartnerEntityDao;

	private final WasTblOrderEntityDao tblOrderEntityDao;

	private final WasMstItemEntityDao mstItemEntityDao;

	/**
	 * B201:取引先検索一覧取得
	 * 
	 * @param likePartnerName 検索条件となる取引先名（あいまい検索）
	 * @return 検索条件に該当する取引先データ（{@link PartnerListRowResponse}））のリスト
	 */
	@GetMapping("/partner/list")
	@Transactional
	public List<B201ResponseRow> getList(
			@RequestParam(name = "likePartnerName", required = false) String likePartnerName) {

		// 取引先一覧を取得する
		var list = mstPartnerEntityDao.searchPartnerListByConditions(likePartnerName).stream().map(v -> {
			var result = new B201ResponseRow();
			modelMapper.map(v, result);

			// 削除可否判定を追加する。
			// 受注データによる利用をチェックする。
			var isOrder = tblOrderEntityDao.isExistsOrderByPartner(v.getPartnerId());
			// 商品の仕入れ先での利用をチェックする。
			var isItem = mstItemEntityDao.isExistsItemByPartner(v.getPartnerId());

			// 削除可能フラグを設定する
			result.setIsCanDeleted(!isOrder && !isItem);

			return result;
		}).collect(Collectors.toList());

		// レスポンス情報にマッピングする
		return list;
	}

}