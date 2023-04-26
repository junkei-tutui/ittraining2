package jp.zein.it.training.was.controller.b001.genericcodelist;

import lombok.Data;

/**
 * B001：汎用コード一覧用行データ
 * <p>
 * 汎用コード選択コントロール用のvalue-nameデータ
 * <p>
 */
@Data
public class B001ResponseRow {

	/** 汎用コード値 */
	private String genericCode;

	/** 汎用コード名称 */
	private String genericCodeName;

}
