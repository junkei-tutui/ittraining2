package jp.zein.it.training.was.controller.b112.orderconfirmed;

import java.text.MessageFormat;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.common.util.TrainingConsts.OrderStatus;
import jp.zein.it.training.was.dao.WasTblOrderEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import lombok.RequiredArgsConstructor;

/**
 * B112:受注確認済更新コントローラー
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class B112Controller {

	private final WasTblOrderEntityDao tblOrderEntityDao;

	/**
	 * B112:受注確認済更新
	 * 
	 * @param request 更新対象の受注データの更新キー{@link B112Request}
	 */
	@PostMapping("/confirmed")
	@Transactional
	public void orderConfirmed(@RequestBody B112Request request) {

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
			// ステータス変更が出来ないためエラーとする。
			throw new TrainingApplicationException("不明な注文受付ステータスコードです。");
		}

		// 未確認以外のステータスの場合は変更ＮＧ
		if (!orderStatus.equals(OrderStatus.UNCONFIRMED)) {
			// Enumに変換できない場合は不明なステータスのためエラーとする。
			throw new TrainingApplicationException(
					MessageFormat.format("この受注の注文受付ステータスを確定済みに更新できません。（注文受付ステータス：{0}）", orderStatus.getCaption()));
		}

		// 受注情報を更新する。
		// 注文ステータスを確認済みに更新する
		orderHeader.setOrderStatus(OrderStatus.CONFIRMED.getValue());
		tblOrderEntityDao.update(orderHeader);
	}

}