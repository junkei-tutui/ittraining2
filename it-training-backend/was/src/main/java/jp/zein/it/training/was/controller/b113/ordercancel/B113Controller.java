package jp.zein.it.training.was.controller.b113.ordercancel;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.common.util.TrainingConsts.OrderLineStatus;
import jp.zein.it.training.common.util.TrainingConsts.OrderStatus;
import jp.zein.it.training.was.dao.WasTblOrderDetailEntityDao;
import jp.zein.it.training.was.dao.WasTblOrderEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import lombok.RequiredArgsConstructor;

/**
 * B113:受注取消コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class B113Controller {

	private final WasTblOrderEntityDao tblOrderEntityDao;

	private final WasTblOrderDetailEntityDao tblOrderDetailEntityDao;

	/**
	 * B113:受注取消
	 * 
	 * @param request 更新対象の受注データの更新キー{@link B113Request}
	 */
	@PostMapping("/cancel")
	@Transactional
	public void orderCancel(@RequestBody B113Request request) {

		// 受注情報を取得する
		var orderHeader = tblOrderEntityDao.selectById(request.getOrderNo());

		// 受注情報の取得チェック
		if (orderHeader == null) {
			// 受注情報がテーブルに存在しない場合はエラー
			throw new TrainingApplicationException("受注情報が存在しません。");
		}

		// 受注情報のステータスチェック
		OrderStatus orderStatus;
		try {
			orderStatus = OrderStatus.codeOf(orderHeader.getOrderStatus());
		} catch (Exception e) {
			// Enumに変換できない場合は不明なステータスのためエラーとする。
			throw new TrainingApplicationException("不明な注文受付ステータスコードです。");
		}

		// 引当済もしくは取消ステータスの場合は変更ＮＧ（一部引当の場合は明細ステータスによる）
		if ((orderStatus.equals(OrderStatus.RESERVED) || orderStatus.equals(OrderStatus.CANCEL))) {
			// ステータス変更が出来ないためエラーとする。
			throw new TrainingApplicationException(
					MessageFormat.format("この受注の注文受付ステータスを確定済みに更新できません。（注文受付ステータス：{0}）", orderStatus.getCaption()));
		}

		// 受注明細情報を取得する。
		var orderDetailList = tblOrderDetailEntityDao.selectByOrderNo(request.getOrderNo());

		// 受注情報の取得チェック
		if (Optional.ofNullable(orderDetailList).isEmpty()) {
			// 受注情報がテーブルに存在しない場合はエラー
			throw new TrainingApplicationException("受注情報が存在しません。");
		}

		// 受注情報をチェックする
		for (var orderDetail : orderDetailList) {
			// 明細ステータスをチェックする。
			var orderLineStatus = OrderLineStatus.codeOf(orderDetail.getOrderLineStatus());
			// 受注明細ステータス毎に処理を分岐する。
			switch (orderLineStatus) {
			case BEFORERESERVED:
			case NOTRESERVED:
				// 受注明細を取消に更新する。
				// 注文ステータスを取消済みに更新する
				orderDetail.setOrderLineStatus(OrderLineStatus.CANCEL.getValue());
				// 販売合計金額を0に設定する
				orderDetail.setSubtotalSellingPrice(BigDecimal.ZERO);
				orderDetail.setSubtotalSellingPriceIntax(BigDecimal.ZERO);
				tblOrderDetailEntityDao.update(orderDetail);
				break;
			case RESERVED:
				// 引当済の明細がある場合は取消不可としてエラー
				throw new TrainingApplicationException(
						MessageFormat.format("引当済みの明細が存在するため取消できません。（明細No：{0}）", orderDetail.getLineNo()));
			default:
				break;
			}
		}

		// 受注情報を更新する。
		// 注文ステータスを取消済みに更新する
		orderHeader.setOrderStatus(OrderStatus.CANCEL.getValue());
		// 販売合計金額を0に設定する
		orderHeader.setTotalOrderAmount(BigDecimal.ZERO);
		orderHeader.setTotalOrderAmountIntax(BigDecimal.ZERO);
		tblOrderEntityDao.update(orderHeader);
	}

}