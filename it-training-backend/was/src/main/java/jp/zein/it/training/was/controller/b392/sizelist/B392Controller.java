package jp.zein.it.training.was.controller.b392.sizelist;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.dao.WasMstSizeEntityDao;
import lombok.RequiredArgsConstructor;

/**
 * B392:サイズコード一覧取得コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/item/pattern")
public class B392Controller {

	private final WasMstSizeEntityDao mstSizeEntityDao;

	/**
	 * B392:サイズコード一覧取得
	 * 
	 * @return 全サイズのコンボボックス選択肢（{@link B392ResponseRow}）リスト
	 */
	@GetMapping("/size/list")
	@Transactional
	public List<B392ResponseRow> getSizeList() {

		// サイズコード一覧を取得する
		var list = mstSizeEntityDao.selectList().stream().map(v -> {
			var result = new B392ResponseRow();
			// カラム名が異なるため、手動でマッピング
			result.setCode(v.getSizeCode());
			result.setName(v.getSizeName());
			return result;
		}).collect(Collectors.toList());

		// レスポンス情報にマッピングする
		return list;

	}

}