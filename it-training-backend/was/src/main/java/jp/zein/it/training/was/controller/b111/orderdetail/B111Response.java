package jp.zein.it.training.was.controller.b111.orderdetail;

import jp.zein.it.training.was.service.order.dto.OrderDetailDto;
import jp.zein.it.training.was.service.order.dto.OrderHeaderDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * B111：受注詳細取得用データ（受注詳細データ）
 * <p>
 * 共通受注ヘッダーデータモデル（{@link OrderHeaderDto}）に<br>
 * 共通受注明細データモデル（{@link OrderDetailDto}）のリストを持つ<br>
 * １受注の詳細データモデル
 * <p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class B111Response extends OrderHeaderDto {

	/**
	 * 受注明細リスト
	 */
	private java.util.List<OrderDetailDto> orderDetailList;

}
