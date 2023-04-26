package jp.zein.it.training.was.controller.b392.sizelist;

import lombok.Data;

/**
 * B392:サイズコード一覧取得用行データ
 * <p>
 * サイズ選択コントロール用のcode-nameデータ
 * <p>
 */
@Data
public class B392ResponseRow {

	/** コード値 */
	private String code;

	/** コード名称 */
	private String name;

}
