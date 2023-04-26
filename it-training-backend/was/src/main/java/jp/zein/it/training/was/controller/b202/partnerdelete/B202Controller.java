package jp.zein.it.training.was.controller.b202.partnerdelete;

import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.dao.WasMstItemEntityDao;
import jp.zein.it.training.was.dao.WasMstPartnerEntityDao;
import jp.zein.it.training.was.dao.WasTblOrderEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import lombok.RequiredArgsConstructor;

/**
 * B202:取引先削除コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/partner")
public class B202Controller {

	private final WasMstPartnerEntityDao mstPartnerEntityDao;

	private final WasTblOrderEntityDao tblOrderEntityDao;

	private final WasMstItemEntityDao mstItemEntityDao;

	/**
	 * B202:取引先削除
	 * 
	 * @param partnerId 削除対象の取引先ID
	 */
	@DeleteMapping()
	@Transactional
	public void delete(@RequestParam(name = "partnerId", required = true) String partnerId) {

		// 取引先の存在チェックを行う
		var partner = mstPartnerEntityDao.selectById(partnerId);
		// 指定の取引先が有効でない場合はエラーとする。
		if (Objects.isNull(partner)) {
			// 取引先マスタが存在しない場合はエラー
			throw new TrainingApplicationException("対象の取引先データは削除された可能性があります。管理者にお問い合わせください。");
		}

		// 削除可否チェックを行う
		// 受注データによる利用をチェックする。
		var isOrder = tblOrderEntityDao.isExistsOrderByPartner(partnerId);
		// 商品の仕入れ先での利用をチェックする。
		var isItem = mstItemEntityDao.isExistsItemByPartner(partnerId);

		// 受注のクライアントもしくは商品の仕入れ先に使用されていれば削除不可とする。
		if (isOrder || isItem) {
			// 取引先マスタが存在しない場合はエラー
			throw new TrainingApplicationException("対象の取引先データは使用されているため、削除できません。");
		}

		// 取引先レコードの削除フラグをONにする。
		partner.setIsDelete(true);

		// 取引先を論理削除する。
		mstPartnerEntityDao.update(partner);
	}

}