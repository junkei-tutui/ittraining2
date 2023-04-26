package jp.zein.it.training.was.controller.b211.partnerdetail;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.dao.WasMstPartnerEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import lombok.RequiredArgsConstructor;

/**
 * B211:取引先詳細取得コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/partner")
public class B211Controller {

	private final ModelMapper modelMapper;

	private final WasMstPartnerEntityDao mstPartnerEntityDao;

	/**
	 * B211:取引先詳細取得
	 * 
	 * @param partnerId 詳細情報取得対象の取引先ID
	 * @return 取引先IDより取得した取引先詳細データ（{@link B211Response}）
	 */
	@GetMapping()
	@Transactional
	public B211Response get(@RequestParam(name = "partnerId", required = true) String partnerId) {

		// 取引先マスタを取得する
		var partnerDetail = mstPartnerEntityDao.selectById(partnerId);

		// 取引先マスタの存在取得チェック
		if (partnerDetail == null) {
			// 取引先マスタが存在しない場合はエラー
			throw new TrainingApplicationException("対象の取引先データは削除された可能性があります。管理者にお問い合わせください。");
		}

		// レスポンス情報にマッピングする
		return modelMapper.map(partnerDetail, B211Response.class);
	}

}