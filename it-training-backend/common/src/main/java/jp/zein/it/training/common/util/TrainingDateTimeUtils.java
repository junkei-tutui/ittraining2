package jp.zein.it.training.common.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 日時ユーティリティ
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainingDateTimeUtils {

	/**
	 * 翌月
	 */
	public static final int NEXT_MONTH = 1;

	/**
	 * 1か月
	 */
	public static final int MONTH_1 = 1;

	/**
	 * 12か月
	 */
	public static final int MONTH_12 = 12;

	/**
	 * 1年
	 */
	public static final int YEAR_1 = 1;

	/**
	 * 1日
	 */
	public static final int DAY_1 = 1;

	/**
	 * 月初(1日)
	 */
	public static final int START_DAY_OF_MONTH = 1;

	/**
	 * 24時間
	 */
	public static final int HOURS_OF_DAY = 24;

	/**
	 * 日付フォーマット
	 */
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	/**
	 * 年月フォーマット
	 */
	private static final String YEAR_MONTH_FORMAT = "yyyy/MM";

	/**
	 * 月末日付を取得
	 *
	 * @param d 日付
	 * @return 月末日付
	 */
	public static LocalDate getEndOfMonth(LocalDate d) {
		return getStartOfMonth(d).plusMonths(MONTH_1).minusDays(DAY_1);
	}

	/**
	 * 月初日付を取得
	 *
	 * @param d 日付
	 * @return 月初日付
	 */
	public static LocalDate getStartOfMonth(LocalDate d) {
		return LocalDate.of(d.getYear(), d.getMonth(), START_DAY_OF_MONTH);
	}

	/**
	 * {@link LocalDate}から年月(YYYYMM)に変換
	 *
	 * @param dt {@link LocalDate}
	 * @return 年月(YYYYMM)
	 */
	public static int locateDateToYm(LocalDate dt) {
		return dt.getYear() * 100 + dt.getMonthValue();
	}

	/**
	 * 年月(YYYYMM)から{@link LocalDate}に変換
	 *
	 * @param ym 年月(YYYYMM)
	 * @return {@link LocalDate}
	 */
	public static LocalDate ymToLocateDate(int ym) {
		return LocalDate.of(ym / 100, ym % 100, START_DAY_OF_MONTH);
	}

	/**
	 * {@link LocalDate}から「yyyy/MM/dd」形式の文字列に変換
	 *
	 * @param dt {@link LocalDate}
	 * @return 「yyyy/MM/dd」形式の文字列
	 */
	public static String toDateString(LocalDate dt) {
		return dt.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
	}

	/**
	 * {@link LocalDate}から「yyyy/MM」形式の文字列に変換
	 *
	 * @param dt {@link LocalDate}
	 * @return 「yyyy/MM」形式の文字列
	 */
	public static String toYearMonthString(LocalDate dt) {
		return dt.format(DateTimeFormatter.ofPattern(YEAR_MONTH_FORMAT));
	}

	/**
	 * 年月(YYYYMM)から「yyyy/MM」形式の文字列に変換
	 *
	 * @param ym 年月(YYYYMM)
	 * @return 「yyyy/MM」形式の文字列
	 */
	public static String toYearMonthString(int ym) {
		return toYearMonthString(ymToLocateDate(ym));
	}
}
