package jp.zein.it.training.was.controller.b321.productinsert;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.common.generate.entity.MstItemEntity;
import jp.zein.it.training.was.dao.WasMstItemEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import jp.zein.it.training.was.service.product.ProductCommonService;
import lombok.RequiredArgsConstructor;

/**
 * B321:商品（SKU）登録コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class B321Controller {

	private final ModelMapper modelMapper;

	private final WasMstItemEntityDao mstItemEntityDao;

	private final ProductCommonService productCommonService;

	/**
	 * B321:商品（SKU）登録
	 * 
	 * @param request 新規登録する商品情報（{@link B321Request}）
	 */
	@PutMapping()
	@Transactional
	public void insert(@RequestBody B321Request request) {

		// 商品の共通チェック処理を実施する
		// ※ エラー時は処理内でExceptionを発生させるため、ハンドリング不要。
		productCommonService.commonProductCheck(request);

		// 商品マスタの存在チェックを行う
		var item = mstItemEntityDao.selectByIdIncLD(request.getItemCode());
		// 指定の商品で有効なレコードが存在する場合はエラーとする。
		if (!Objects.isNull(item) && !item.getIsDelete()) {
			// 商品マスタが存在かつ、削除フラグがOFFの場合はエラー
			throw new TrainingApplicationException("対象の商品データは既に存在しています。");
		}

		// レコードの存在有無で、処理を変更する。
		if (Objects.isNull(item)) {
			// 新規レコードを作成する。
			item = new MstItemEntity();
			// 入力された情報を登録用エンティティにマッピングする。
			modelMapper.map(request, item);
			// 固定情報の情報のみ再設定
			// 取引先に仕入先を設定
			item.setPartnerId(request.getSupplierId());
			// 削除フラグ（OFF）
			item.setIsDelete(false);

			// 共通の商品情報設定処理を行う。（仕入価格（税込）、販売価格（税込）の設定）
			productCommonService.commonProductSetting(request, item);

			// 商品マスタに登録する。
			mstItemEntityDao.insert(item);
		} else {
			// 入力された情報で取引先情報を更新する。
			modelMapper.map(request, item);
			// 取引先に仕入先を設定
			item.setPartnerId(request.getSupplierId());
			// 削除フラグは有効に変更する。
			item.setIsDelete(false);

			// 共通の商品情報設定処理を行う。（仕入価格（税込）、販売価格（税込）の設定）
			productCommonService.commonProductSetting(request, item);

			// 商品マスタを更新する。
			mstItemEntityDao.update(item);
		}

		// 商品（SKU）の情報を一括更新する。
		productCommonService.replacementSKU(item, request);
	}

}