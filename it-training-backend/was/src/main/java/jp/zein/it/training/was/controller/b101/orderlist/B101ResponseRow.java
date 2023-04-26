package jp.zein.it.training.was.controller.b101.orderlist;

import jp.zein.it.training.was.service.order.dto.OrderHeaderDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * B101：受注一覧取得用行データ（受注データ）
 * <p>
 * 共通受注ヘッダーデータモデル（{@link OrderHeaderDto}）の派生
 * <p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class B101ResponseRow extends OrderHeaderDto {

}
