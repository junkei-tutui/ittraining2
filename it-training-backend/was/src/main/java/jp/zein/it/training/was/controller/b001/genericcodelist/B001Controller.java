package jp.zein.it.training.was.controller.b001.genericcodelist;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.dao.WasMstGenericCodeEntityDao;
import lombok.RequiredArgsConstructor;

/**
 * B001: 汎用コード一覧取得コントローラー
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/genericcode")
public class B001Controller {

	private final ModelMapper modelMapper;

	private final WasMstGenericCodeEntityDao mstGenericCodeEntityDao;

	/**
	 * B001：汎用コード一覧取得
	 * 
	 * @param genericCodeTypeId 取得する汎用コード種類ID
	 * @return 指定した汎用コード種類のコンボボックス選択肢（{@link B001ResponseRow}）リスト
	 */
	@GetMapping("/list")
	@Transactional
	public List<B001ResponseRow> getList(
			@RequestParam(name = "genericCodeTypeId", required = true) String genericCodeTypeId) {

		// 汎用コード一覧を取得する
		var list = mstGenericCodeEntityDao.selectListByGenericCodeTypeId(genericCodeTypeId);

		// レスポンス情報にマッピングする
		return modelMapper.map(list, new TypeToken<List<B001ResponseRow>>() {
		}.getType());
	}
}