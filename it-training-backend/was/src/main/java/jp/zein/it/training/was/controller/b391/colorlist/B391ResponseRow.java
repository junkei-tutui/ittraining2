package jp.zein.it.training.was.controller.b391.colorlist;

import lombok.Data;

/**
 * B391:カラーコード一覧取得用行データ
 * <p>
 * カラー選択コントロール用のcode-nameデータ
 * <p>
 */
@Data
public class B391ResponseRow {

	/** コード値 */
	private String code;

	/** コード名称 */
	private String name;

}
