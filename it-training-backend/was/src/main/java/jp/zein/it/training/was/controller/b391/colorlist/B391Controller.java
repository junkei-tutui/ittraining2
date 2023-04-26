package jp.zein.it.training.was.controller.b391.colorlist;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.dao.WasMstColorEntityDao;
import lombok.RequiredArgsConstructor;

/**
 * B391:カラーコード一覧取得コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/item/pattern")
public class B391Controller {

	private final WasMstColorEntityDao mstColorEntityDao;

	/**
	 * B391:カラーコード一覧取得
	 * 
	 * @return 全カラーのコンボボックス選択肢（{@link B391ResponseRow}）リスト
	 */
	@GetMapping("/color/list")
	@Transactional
	public List<B391ResponseRow> getColorList() {

		// カラーコード一覧を取得する
		var list = mstColorEntityDao.selectList().stream().map(v -> {
			var result = new B391ResponseRow();
			// カラム名が異なるため、手動でマッピング
			result.setCode(v.getColorCode());
			result.setName(v.getColorName());
			return result;
		}).collect(Collectors.toList());

		// レスポンス情報にマッピングする
		return list;
	}

}