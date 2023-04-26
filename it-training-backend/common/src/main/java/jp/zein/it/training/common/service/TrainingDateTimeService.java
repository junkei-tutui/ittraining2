package jp.zein.it.training.common.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *  日時取得サービス
 *
 */
public interface TrainingDateTimeService {
	/**
	 * 現在日時を取得
	 *
	 * @return 現在日時
	 */
	LocalDateTime now();

	/**
	 * 本日日付を取得
	 *
	 * @return	本日日付
	 */
	LocalDate today();
}
