package jp.zein.it.training.was.controller.b291.supplierlist;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.dao.WasMstPartnerEntityDao;
import lombok.RequiredArgsConstructor;

/**
 * B291:仕入先一覧取得コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
public class B291Controller {

	private final WasMstPartnerEntityDao mstPartnerEntityDao;

	/**
	 * B291:仕入先一覧取得
	 * 
	 * @return 全取引先より仕入先のみに絞り込んだコンボボックス選択肢（{@link B291ResponseRow}）リスト
	 */
	@GetMapping("/supplier/list")
	@Transactional
	public List<B291ResponseRow> getList() {

		// 取引先より仕入先一覧を取得する
		var list = mstPartnerEntityDao.getSupplierList().stream().map(v -> {
			var result = new B291ResponseRow();
			// 取引先IDを仕入先IDとして設定
			result.setSupplierId(v.getPartnerId());
			// 取引先名を仕入先名として設定
			result.setSupplierName(v.getPartnerName());
			return result;
		}).collect(Collectors.toList());

		// レスポンス情報にマッピングする
		return list;
	}

}