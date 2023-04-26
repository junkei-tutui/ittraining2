package jp.zein.it.training.was.controller.b302.productdelete;

import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.dao.WasMstItemEntityDao;
import jp.zein.it.training.was.dao.WasTblOrderDetailEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import lombok.RequiredArgsConstructor;

/**
 * B302:商品削除コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class B302Controller {

	private final WasMstItemEntityDao mstItemEntityDao;

	private final WasTblOrderDetailEntityDao tblOrderDetailEntityDao;

	/**
	 * B302:商品削除
	 * 
	 * @param itemCode 削除対象の商品コード
	 */
	@DeleteMapping()
	@Transactional
	public void delete(@RequestParam(name = "itemCode", required = true) String itemCode) {

		// 商品の存在チェックを行う
		var item = mstItemEntityDao.selectById(itemCode);

		// 指定の商品が存在しない場合はエラーとする。
		if (Objects.isNull(item)) {
			// 取引先マスタが存在しない場合はエラー
			throw new TrainingApplicationException("対象の商品データは削除された可能性があります。管理者にお問い合わせください。");
		}

		// 削除可否チェックを行う
		// 受注データによる利用をチェックする。
		var isOrder = tblOrderDetailEntityDao.isExistsOrderDetailByItem(itemCode);

		// 受注の受注商品に使用されていれば削除不可とする。
		if (isOrder) {
			// 取引先マスタが存在しない場合はエラー
			throw new TrainingApplicationException("対象の商品データは使用されているため、削除できません。");
		}

		// 商品レコードの削除フラグをONにする。
		item.setIsDelete(true);

		// 商品を論理削除する。
		mstItemEntityDao.update(item);
	}

}