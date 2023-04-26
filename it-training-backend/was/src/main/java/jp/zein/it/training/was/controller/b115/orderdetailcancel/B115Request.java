package jp.zein.it.training.was.controller.b115.orderdetailcancel;

import lombok.Data;

/**
 * B115:受注明細取消用データ（更新キー情報）
 *
 */
@Data
public class B115Request {

	/** 受注番号 */
	private int orderNo;

	/** 明細番号 */
	private int lineNo;

}
