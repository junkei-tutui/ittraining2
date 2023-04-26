package jp.zein.it.training.was.controller.b291.supplierlist;

import lombok.Data;

/**
 * B291:仕入先一覧取得用行データ
 * <p>
 * 仕入先選択コントロール用のvalue-nameデータ
 * <p>
 */
@Data
public class B291ResponseRow {

	/** 仕入先ID（取引先ID） */
	private String supplierId;

	/** 仕入先名（取引先名） */
	private String supplierName;

}
