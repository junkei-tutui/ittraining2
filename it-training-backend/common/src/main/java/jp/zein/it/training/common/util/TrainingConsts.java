package jp.zein.it.training.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 定数値定義
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TrainingConsts {

	/**
	 * 無期限を表す日時。
	 */
	public static final LocalDateTime INDEFINITELY_DATETIME = LocalDateTime.of(LocalDate.of(9999, 12, 31),
			LocalTime.MIN);

	/**
	 * 無期限を表す日付。
	 */
	public static final LocalDate INDEFINITELY_DATE = LocalDate.of(9999, 12, 31);

	/**
	 * CSVファイルで連携する際の日付の文字列フォーマット
	 */
	public static final String CSV_DATE_FORMAT = "yyyy/MM/dd";

	/**
	 * CSVファイルに連携する際の日付のフォーマッター
	 */
	public static final DateTimeFormatter CSV_DATE_FORMETTER = DateTimeFormatter.ofPattern(CSV_DATE_FORMAT);

	/**
	 * CSVファイル名等に付加する現在日時のフォーマット
	 */
	public static final DateTimeFormatter FILE_DATETIME_FORMETTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	/**
	 * CSVファイル名等に付加する現在日付のフォーマット
	 */
	public static final DateTimeFormatter FILE_DATE_FORMETTER = DateTimeFormatter.ofPattern("yyyyMMdd");

	/**
	 * IDに日付を付与する際の文字列フォーマット
	 */
	public static final String ID_DATE_FORMAT = "yyyyMMdd";

	/**
	 * エラーファイルに付与される接頭文字
	 */
	public static final String ERROR_FILE_PRIFIX = "err_";

	/**
	 * バックアップファイルに付与される接頭文字
	 */
	public static final String BACKUP_FILE_PRIFIX = "bak_";

	/**
	 * 返却ファイルに付与される接頭文字
	 */
	public static final String RETURN_FILE_PRIFIX = "ret_";

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class TrainingCommon {
		/**
		 * 基本言語コード
		 */
		public static final String LANGUAGE_CODE = "ja";

		/**
		 * 基本言語コードに該当するロケール
		 */
		public static final Locale BASIC_LOCALE = Locale.JAPANESE;
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class GenericCodeType {
		/**
		 * カテゴリ
		 */
		public static final String CATEGORY_CODE = "001";

		/**
		 * 分類
		 */
		public static final String CLASSIFICATION_CODE = "002";

		/**
		 * シーズン
		 */
		public static final String SEASON_CODE = "003";

	}

	/**
	 * 受注受付ステータス
	 *
	 */
	public enum OrderStatus {
		/**
		 * 未確認
		 */
		UNCONFIRMED("01", "未確認"),

		/**
		 * 確認済
		 */
		CONFIRMED("02", "確認"),

		/**
		 * 引当済
		 */
		RESERVED("03", "引当済"),

		/**
		 * 一部引当不可
		 */
		SOMENOTRESERVED("04", "一部引当不可"),

		/**
		 * 引当不可
		 */
		NOTRESERVED("05", "引当不可"),

		/**
		 * 受注取消
		 */
		CANCEL("06", "受注取消");

		private String value;
		private String caption;
		private static final Map<String, OrderStatus> map = new HashMap<>();
		private static final Set<OrderStatus> captionTypes = new HashSet<>();
		private static final List<String> captionTypesValues;

		private OrderStatus(String value, String caption) {
			this.value = value;
			this.caption = caption;
		}

		static {
			for (var item : OrderStatus.values()) {
				map.put(item.value, item);
				if (StringUtils.isNotEmpty(item.caption)) {
					captionTypes.add(item);
				}
			}
			captionTypesValues = captionTypes.stream().map(OrderStatus::getValue).collect(Collectors.toList());
		}

		public static Set<OrderStatus> getCaptionTypes() {
			return captionTypes;
		}

		public static OrderStatus codeOf(String value) {
			return (OrderStatus) map.get(value);
		}

		public static List<String> getCaptionTypeValues() {
			return captionTypesValues;
		}

		public String getValue() {
			return value;
		}

		public String getCaption() {
			return caption;
		}
	}

	/**
	 * 受注明細ステータス
	 *
	 */
	public enum OrderLineStatus {
		/**
		 * 引当前
		 */
		BEFORERESERVED("01", "引当前"),

		/**
		 * 引当済
		 */
		RESERVED("02", "引当済"),

		/**
		 * 引当不可
		 */
		NOTRESERVED("03", "引当不可"),

		/**
		 * 取消
		 */
		CANCEL("04", "取消");

		private String value;
		private String caption;
		private static Map<String, OrderLineStatus> map = new HashMap<>();
		private static final Set<OrderLineStatus> captionTypes = new HashSet<>();
		private static final List<String> captionTypesValues;

		private OrderLineStatus(String value, String caption) {
			this.value = value;
			this.caption = caption;
		}

		static {
			for (var item : OrderLineStatus.values()) {
				map.put(item.value, item);
				if (StringUtils.isNotEmpty(item.caption)) {
					captionTypes.add(item);
				}

				captionTypes.add(item);
			}
			captionTypesValues = captionTypes.stream().map(OrderLineStatus::getValue).collect(Collectors.toList());
		}

		public static OrderLineStatus codeOf(String value) {
			return (OrderLineStatus) map.get(value);
		}

		public static Set<OrderLineStatus> getCaptionTypes() {
			return captionTypes;
		}

		public static List<String> getCaptionTypeValues() {
			return captionTypesValues;
		}

		public String getValue() {
			return value;
		}

		public String getCaption() {
			return caption;
		}
	}

	/**
	 * 取引区分
	 *
	 */
	public enum PartnerType {
		/**
		 * 仕入兼販売先
		 */
		ALL(0, "仕入兼販売先", true, true),

		/**
		 * 仕入先
		 */
		SUPPLIER(1, "仕入先", false, true),

		/**
		 * 販売先
		 */
		CLIENT(2, "販売先", true, false);

		private int value;
		private String caption;
		private boolean client;
		private boolean supplier;
		private static final Map<Integer, PartnerType> map = new HashMap<>();
		private static final Set<PartnerType> captionTypes = new HashSet<>();
		private static final Set<PartnerType> clientTypes = new HashSet<>();
		private static final Set<PartnerType> supplierTypes = new HashSet<>();
		private static final List<Integer> captionTypesValues;
		private static final List<Integer> clientTypesValues;
		private static final List<Integer> supplierTypesValues;

		private PartnerType(int value, String caption, boolean client, boolean supplier) {
			this.value = value;
			this.caption = caption;
			this.client = client;
			this.supplier = supplier;
		}

		static {
			for (var item : PartnerType.values()) {
				map.put(item.value, item);
				if (StringUtils.isNotEmpty(item.caption)) {
					captionTypes.add(item);
				}
				if (item.client) {
					clientTypes.add(item);
				}
				if (item.supplier) {
					supplierTypes.add(item);
				}
			}
			captionTypesValues = captionTypes.stream().map(PartnerType::getValue).collect(Collectors.toList());
			clientTypesValues = clientTypes.stream().map(PartnerType::getValue).collect(Collectors.toList());
			supplierTypesValues = supplierTypes.stream().map(PartnerType::getValue).collect(Collectors.toList());
		}

		public static PartnerType valueOf(int value) {
			return (PartnerType) map.get(value);
		}

		public static Set<PartnerType> getCaptionTypes() {
			return captionTypes;
		}

		public static List<Integer> getCaptionTypeValues() {
			return captionTypesValues;
		}

		public static Set<PartnerType> getClientTypes() {
			return clientTypes;
		}

		public static List<Integer> getClientTypeValues() {
			return clientTypesValues;
		}

		public static Set<PartnerType> getSupplierTypes() {
			return supplierTypes;
		}

		public static List<Integer> getSupplierTypeValues() {
			return supplierTypesValues;
		}

		public int getValue() {
			return value;
		}

		public String getCaption() {
			return caption;
		}

		public boolean isClient() {
			return client;
		}

		public boolean isSupplier() {
			return supplier;
		}
	}

}
