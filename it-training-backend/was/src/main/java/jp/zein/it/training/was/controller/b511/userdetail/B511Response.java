package jp.zein.it.training.was.controller.b511.userdetail;

import lombok.Data;

/**
 * B511:ユーザー情報取得用データ（ユーザーデータ）
 * 
 */
@Data
public class B511Response {

	/**
	 * ユーザーID
	 */
	private String userId;

	/**
	 * ユーザー名
	 */
	private String userName;

}
