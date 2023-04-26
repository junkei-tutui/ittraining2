package jp.zein.it.training.was.controller.b222.partnerupdate;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.controller.b221.partnerinsert.B221Request;
import jp.zein.it.training.was.dao.WasMstPartnerEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import lombok.RequiredArgsConstructor;

/**
 * B222:取引先更新コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/partner")
public class B222Controller {

	private final ModelMapper modelMapper;

	private final WasMstPartnerEntityDao mstPartnerEntityDao;

	/**
	 * B222:取引先更新
	 * <p>
	 * リクエストされた取引先情報の取引先コードをキーに全ての項目を更新する。
	 * <p>
	 * 
	 * @param request 更新する取引先情報（{@link B221Request}）
	 */
	@PostMapping()
	@Transactional
	public void update(@RequestBody B222Request request) {

		// 取引先の存在チェックを行う
		var partner = mstPartnerEntityDao.selectById(request.getPartnerId());
		// 指定の取引先が有効でない場合はエラーとする。
		if (Objects.isNull(partner)) {
			// 取引先マスタが存在しない場合はエラー
			throw new TrainingApplicationException("対象の取引先データは削除された可能性があります。管理者にお問い合わせください。");
		}

		// 入力された情報で取引先情報を更新する。
		modelMapper.map(request, partner);

		// 取引先テーブルを更新する。
		mstPartnerEntityDao.update(partner);
	}

}