package jp.zein.it.training.was.controller.b115.orderdetailcancel;

import java.math.BigDecimal;
import java.text.MessageFormat;

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
 * B115:受注明細取消コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class B115Controller {

	private final WasTblOrderEntityDao tblOrderEntityDao;

	private final WasTblOrderDetailEntityDao tblOrderDetailEntityDao;

	/**
	 * B115:受注明細取消
	 * 
	 * @param request 更新対象の受注明細データの更新キー{@link B115Request}
	 */
	@PostMapping("/detail/cancel")
	@Transactional
	public void orderDetailCancel(@RequestBody B115Request request) {

		// 受注情報を取得する
		var orderHeader = tblOrderEntityDao.selectById(request.getOrderNo());

		// 受注情報の取得チェック
		if (orderHeader == null) {
			// 受注情報がテーブルに存在しない場合はエラー
			throw new TrainingApplicationException("受注情報が存在しません。");
		}

		// 受注明細情報を取得する
		var orderDetail = tblOrderDetailEntityDao.selectById(request.getOrderNo(), request.getLineNo());

		// 受注情報の取得チェック
		if (orderDetail == null) {
			// 受注情報がテーブルに存在しない場合はエラー
			throw new TrainingApplicationException("受注情報が存在しません。");
		}

		// 受注明細のステータスチェック
		OrderLineStatus orderLineStatus;
		try {
			orderLineStatus = OrderLineStatus.codeOf(orderDetail.getOrderLineStatus());
		} catch (Exception e) {
			// ステータス変更が出来ないためエラーとする。
			throw new TrainingApplicationException("不明な受付明細ステータスコードです。");
		}

		// 引当済、取消のステータスの場合は取消ＮＧ
		if ((orderLineStatus.equals(OrderLineStatus.RESERVED) || orderLineStatus.equals(OrderLineStatus.CANCEL))) {
			throw new TrainingApplicationException(
					MessageFormat.format("この受注明細は取消できません。（受注明細ステータス：{0}）", orderLineStatus.getCaption()));
		}

		// 受注データの合計金額を取消分減算する。
		// 取消以外の場合は注文受付ステータスはそのままとする。（金額のみ変更）
		orderHeader
				.setTotalOrderAmount(orderHeader.getTotalOrderAmount().subtract(orderDetail.getSubtotalSellingPrice()));
		orderHeader.setTotalOrderAmountIntax(
				orderHeader.getTotalOrderAmountIntax().subtract(orderDetail.getSubtotalSellingPriceIntax()));

		// 受注明細ステータスを取消に更新する
		orderDetail.setOrderLineStatus(OrderLineStatus.CANCEL.getValue());
		orderDetail.setSubtotalSellingPrice(BigDecimal.ZERO);
		orderDetail.setSubtotalSellingPriceIntax(BigDecimal.ZERO);
		tblOrderDetailEntityDao.update(orderDetail);

		// 受注内の状態をチェックし、全て取消状態になった場合は受注受付ステータスを取消に更新する。
		var list = tblOrderDetailEntityDao.selectByOrderNo(request.getOrderNo());
		boolean isAllCancel = true;
		for (var data : list) {
			// 取消以外の受注明細ステータスの場合はフラグをおろす
			if (!data.getOrderLineStatus().equals(OrderLineStatus.CANCEL.getValue())) {
				isAllCancel = false;
				break;
			}
		}
		;

		// 明細の状態によりヘッダの値を更新する。
		if (isAllCancel) {
			// 明細が全て取消の場合
			// 注文ステータスを取消済みに更新する
			orderHeader.setOrderStatus(OrderStatus.CANCEL.getValue());
			// 販売合計金額を0に設定する
			orderHeader.setTotalOrderAmount(BigDecimal.ZERO);
			orderHeader.setTotalOrderAmountIntax(BigDecimal.ZERO);
		}
		tblOrderEntityDao.update(orderHeader);
	}

}