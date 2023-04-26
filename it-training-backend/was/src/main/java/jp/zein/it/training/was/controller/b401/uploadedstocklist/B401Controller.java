package jp.zein.it.training.was.controller.b401.uploadedstocklist;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.dao.WasTblStockCaptureEntityDao;
import lombok.RequiredArgsConstructor;

/**
 * B401:在庫取込結果一覧取得
 *
 */
@RestController
@RequiredArgsConstructor
public class B401Controller {

	private final ModelMapper modelMapper;

	private final WasTblStockCaptureEntityDao tblStockCaptureEntityDao;

	/**
	 * B401:在庫取込結果一覧取得
	 * 
	 * @return {@link List<B401ResponseRow>}
	 */
	@GetMapping("/stock/list")
	@Transactional
	public List<B401ResponseRow> getList() {

		// 在庫取込結果一覧を取得する
		var list = tblStockCaptureEntityDao.selectListByAll();

		// レスポンス情報にマッピングする
		return modelMapper.map(list, new TypeToken<List<B401ResponseRow>>() {
		}.getType());
	}

}