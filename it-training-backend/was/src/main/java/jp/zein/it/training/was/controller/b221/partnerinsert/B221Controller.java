package jp.zein.it.training.was.controller.b221.partnerinsert;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.common.generate.entity.MstPartnerEntity;
import jp.zein.it.training.common.util.TrainingConsts.PartnerType;
import jp.zein.it.training.was.dao.WasMstPartnerEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import lombok.RequiredArgsConstructor;

/**
 * B221:取引先登録コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/partner")
public class B221Controller {

	private final ModelMapper modelMapper;

	private final WasMstPartnerEntityDao mstPartnerEntityDao;

	/**
	 * B221:取引先登録
	 * 
	 * @param request 新規登録する取引先情報（{@link B221Request}）
	 */
	@PutMapping()
	@Transactional
	public void insert(@RequestBody B221Request request) {

		// 取引先の存在チェックを行う
		var partner = mstPartnerEntityDao.selectByIdIncLD(request.getPartnerId());
		// 指定の取引先IDで有効なレコードが存在する場合はエラーとする。
		if (!Objects.isNull(partner) && !partner.getIsDelete()) {
			// 取引先マスタが存在かつ、削除フラグがOFFの場合はエラー
			throw new TrainingApplicationException("対象の取引先データは既に存在しています。");
		}

		// レコードの存在有無で、処理を変更する。
		if (Objects.isNull(partner)) {
			// 新規レコードを作成する。
			var entryRecode = new MstPartnerEntity();
			// 入力された情報を登録用エンティティにマッピングする。
			modelMapper.map(request, entryRecode);
			// 固定情報の情報のみ再設定
			// 取引区分（仕入兼販売先）
			entryRecode.setPartnerType(PartnerType.ALL.getValue());
			// 削除フラグ（OFF）
			entryRecode.setIsDelete(false);

			// 取引先マスタに登録する。
			mstPartnerEntityDao.insert(entryRecode);

		} else {
			// 入力された情報で取引先情報を更新する。
			modelMapper.map(request, partner);
			// 削除フラグは有効に変更する。
			partner.setIsDelete(false);

			// 取引先マスタを更新する。
			mstPartnerEntityDao.update(partner);
		}
	}

}