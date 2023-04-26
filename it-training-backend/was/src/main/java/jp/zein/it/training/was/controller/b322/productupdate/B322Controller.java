package jp.zein.it.training.was.controller.b322.productupdate;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.controller.b321.productinsert.B321Request;
import jp.zein.it.training.was.dao.WasMstItemEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import jp.zein.it.training.was.service.product.ProductCommonService;
import lombok.RequiredArgsConstructor;

/**
 * B322:商品（SKU）更新コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class B322Controller {

	private final ModelMapper modelMapper;

	private final WasMstItemEntityDao mstItemEntityDao;

	private final ProductCommonService productCommonService;

	/**
	 * B322:商品（SKU）更新
	 * <p>
	 * リクエストされた商品情報の商品コードをキーに全ての項目を更新する。
	 * <p>
	 * 
	 * @param request 更新する商品情報（{@link B321Request}）
	 */
	@PostMapping()
	@Transactional
	public void update(@RequestBody B322Request request) {

		// 商品の共通チェック処理を実施する
		// ※ エラー時は処理内でExceptionを発生させるため、ハンドリング不要。
		productCommonService.commonProductCheck(request);

		//
		// 商品マスタの存在チェックを行う
		var item = mstItemEntityDao.selectById(request.getItemCode());
		// 指定の商品で有効なレコードが存在する場合はエラーとする。
		if (Objects.isNull(item)) {
			// 商品マスタが存在かつ、削除フラグがOFFの場合はエラー
			throw new TrainingApplicationException("対象の商品データは削除された可能性があります。管理者にお問い合わせください。");
		}

		// 入力された情報で商品情報を更新する。
		modelMapper.map(request, item);

		// 共通の商品情報設定処理を行う。（仕入価格（税込）、販売価格（税込）の設定）
		productCommonService.commonProductSetting(request, item);

		// 商品マスタを更新する。
		mstItemEntityDao.update(item);

		// 商品（SKU）の情報を一括更新する。
		productCommonService.replacementSKU(item, request);
	}

}