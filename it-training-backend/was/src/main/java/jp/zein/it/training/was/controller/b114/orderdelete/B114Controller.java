package jp.zein.it.training.was.controller.b114.orderdelete;

import java.text.MessageFormat;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.common.util.TrainingConsts.OrderStatus;
import jp.zein.it.training.was.dao.WasTblOrderDetailEntityDao;
import jp.zein.it.training.was.dao.WasTblOrderEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import lombok.RequiredArgsConstructor;

/**
 * B114:受注データ削除（論理削除）コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class B114Controller {

	private final WasTblOrderEntityDao tblOrderEntityDao;

	private final WasTblOrderDetailEntityDao tblOrderDetailEntityDao;

	/**
	 * B114:受注データ削除（論理削除）
	 * 
	 * @param request 更新対象の受注データの更新キー{@link B114Request}
	 */
	@PostMapping("/delete")
	@Transactional
	public void orderDelete(@RequestBody B114Request request) {

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

		// 未確認以外のステータスの場合は削除ＮＧ
		if (!orderStatus.equals(OrderStatus.UNCONFIRMED)) {
			throw new TrainingApplicationException(
					MessageFormat.format("この受注データは削除できません。（注文受付ステータス：{0}）", orderStatus.getCaption()));
		}

		// 受注明細を一括で論理削除する
		tblOrderDetailEntityDao.selectByOrderNo(request.getOrderNo()).forEach((v) -> {
			v.setIsDelete(true);
			tblOrderDetailEntityDao.update(v);
		});

		// 受注情報を更新する。
		// 注文ステータスを取消済みに更新する
		orderHeader.setIsDelete(true);
		tblOrderEntityDao.update(orderHeader);
	}

}