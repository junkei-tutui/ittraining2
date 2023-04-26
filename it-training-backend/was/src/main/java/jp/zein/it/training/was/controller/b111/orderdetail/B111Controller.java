package jp.zein.it.training.was.controller.b111.orderdetail;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.controller.b101.orderlist.B101ResponseRow;
import jp.zein.it.training.was.dao.WasMstPartnerEntityDao;
import jp.zein.it.training.was.dao.WasTblOrderDetailEntityDao;
import jp.zein.it.training.was.dao.WasTblOrderEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import jp.zein.it.training.was.service.order.dto.OrderDetailDto;
import lombok.RequiredArgsConstructor;

/**
 * B111:受注詳細取得コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
public class B111Controller {

	private final ModelMapper modelMapper;

	private final WasTblOrderEntityDao tblOrderEntityDao;

	private final WasMstPartnerEntityDao mstPartnerEntityDao;

	private final WasTblOrderDetailEntityDao tblOrderDetailEntityDao;

	/**
	 * B111:受注詳細取得
	 * 
	 * @param orderNo 詳細情報を取得する受注の受注番号
	 * @return 受注番号より取得した受注詳細データ（{@link B101ResponseRow}）
	 */
	@GetMapping("/order/info")
	@Transactional
	public B111Response get(@RequestParam(name = "orderNo", required = true) int orderNo) {

		// 受注情報を取得する
		var orderHeader = tblOrderEntityDao.selectById(orderNo);

		// 受注情報の取得チェック
		if (orderHeader == null) {
			// 受注情報がテーブルに存在しない場合はエラー
			throw new TrainingApplicationException("受注情報が存在しません。");
		}

		// 取引先名を取得する
		var supplierData = mstPartnerEntityDao.selectById(orderHeader.getPartnerId());

		// レスポンス情報にマッピングする
		var response = modelMapper.map(orderHeader, B111Response.class);
		// 取引先（クライアント）の情報を追加
		response.setClientId(supplierData.getPartnerId()); // クライアントID（取引先ID）
		response.setClientName(supplierData.getPartnerName()); // クライアント名（取引先名）

		// 受注明細情報を追加する
		response.setOrderDetailList(tblOrderDetailEntityDao.selectListByOrderNo(orderNo).stream()
				.map(v -> modelMapper.map(v, OrderDetailDto.class)).collect(Collectors.toList()));

		return response;
	}
}