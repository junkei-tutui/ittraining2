package jp.zein.it.training.was.controller.b211.partnerdetail;

import lombok.Data;

/**
 * B211:取引先詳細取得用データ（取引先データ）
 * 
 */
@Data
public class B211Response {

	/** 取引先ID */
	private String partnerId;

	/** 取引先名 */
	private String partnerName;

	/** 取引区分 */
	private Integer partnerType;

	/** 郵便番号 */
	private String zip;

	/** 住所 */
	private String address;

	/** 電話番号 */
	private String telNo;

	/** FAX番号 */
	private String faxNo;

	/** 担当者 */
	private String responsibleParty;

	/** メールアドレス */
	private String mailAddress;

}
