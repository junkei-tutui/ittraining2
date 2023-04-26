package jp.zein.it.training.was.service.stock.csv;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jp.zein.it.training.was.util.csv.CsvBean;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 引当不可受注ファイル（CSVデータ）Bean
 * <p>
 * 在庫引き当てにて引当できなかった受注データ
 * <p>
 */
@Data
@JsonPropertyOrder({ "result", "order_no", "order_subject", "partner_id", "partner_name", "order_date", "delivery_date",
		"delivery_address", "order_status", "line_no", "product_code", "item_name", "classification_code",
		"classification_name", "season_code", "season_name", "color_code", "color_name", "size_code", "size_name",
		"quantity", "selling_price", "selling_price_intax", "order_line_status", "total_selling_price",
		"total_selling_price_intax" })
public class NoStockOrderDataBean implements CsvBean {

	/**
	 * 引当不可受注ファイルCSVの定義
	 */
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class NoStockOrderDataCsv {
		// --------------------------------------------------------------------
		// カラム定義
		// --------------------------------------------------------------------
		/**
		 * 区分
		 */
		public static final String RESULT = "result";

		/**
		 * 受注番号
		 */
		public static final String ORDER_NO = "order_no";

		/**
		 * 受注件名
		 */
		public static final String ORDER_SUBJECT = "order_subject";

		/**
		 * 取引先ID
		 */
		public static final String PARTNER_ID = "partner_id";

		/**
		 * 取引先名
		 */
		public static final String PARTNER_NAME = "partner_name";

		/**
		 * 受注日
		 */
		public static final String ORDER_DATE = "order_date";

		/**
		 * 納期
		 */
		public static final String DELIVERY_DATE = "delivery_date";

		/**
		 * 納品先住所
		 */
		public static final String DELIVERY_ADDRESS = "delivery_address";

		/**
		 * 注文受付ステータス
		 */
		public static final String ORDER_STATUS = "order_status";

		/**
		 * 明細番号
		 */
		public static final String LINE_NO = "line_no";

		/**
		 * SKU
		 */
		public static final String PRODUCT_CODE = "product_code";

		/**
		 * 商品名
		 */
		public static final String ITEM_NAME = "item_name";

		/**
		 * 分類コード
		 */
		public static final String CLASSIFICATION_CODE = "classification_code";

		/**
		 * 分類名
		 */
		public static final String CLASSIFICATION_NAME = "classification_name";

		/**
		 * シーズンコード
		 */
		public static final String SEASON_CODE = "season_code";

		/**
		 * シーズン名
		 */
		public static final String SEASON_NAME = "season_name";

		/**
		 * カラーコード
		 */
		public static final String COLOR_CODE = "color_code";

		/**
		 * カラー名
		 */
		public static final String COLOR_NAME = "color_name";

		/**
		 * サイズコード
		 */
		public static final String SIZE_CODE = "size_code";

		/**
		 * サイズ名
		 */
		public static final String SIZE_NAME = "size_name";

		/**
		 * 数量
		 */
		public static final String QUANTITY = "quantity";

		/**
		 * 販売価格（税抜）
		 */
		public static final String SELLING_PRICE = "selling_price";

		/**
		 * 販売価格（税込）
		 */
		public static final String SELLING_PRICE_INTAX = "selling_price_intax";

		/**
		 * 注文明細ステータス
		 */
		public static final String ORDER_LINE_STATUS = "order_line_status";

		/**
		 * 小計販売価格（税抜）
		 */
		public static final String TOTAL_SELLING_PRICE = "subtotal_selling_price";

		/**
		 * 小計販売価格（税込）
		 */
		public static final String TOTAL_SELLING_PRICE_INTAX = "subtotal_selling_price_intax";

	}

	/**
	 * 区分
	 */
	@JsonProperty(NoStockOrderDataCsv.RESULT)
	@Size(min = 2, max = 2)
	@NotNull
	private String resultType;

	/**
	 * 受注番号
	 */
	@JsonProperty(NoStockOrderDataCsv.ORDER_NO)
	@Max(value = 9999999999L)
	@NotNull
	private int orderNo;

	/**
	 * 受注件名
	 */
	@JsonProperty(NoStockOrderDataCsv.ORDER_SUBJECT)
	@Size(max = 128)
	@NotEmpty
	private String orderSubject;

	/**
	 * 取引先ID（クライアントID）
	 */
	@JsonProperty(NoStockOrderDataCsv.PARTNER_ID)
	@Size(max = 16)
	@NotEmpty
	private String partnerId;

	/**
	 * 取引先名（クライアント名）
	 */
	@JsonProperty(NoStockOrderDataCsv.PARTNER_NAME)
	@Size(max = 64)
	@NotEmpty
	private String partnerName;

	/**
	 * 受注日
	 */
	@JsonProperty(NoStockOrderDataCsv.ORDER_DATE)
	// TODO:直接LocalDate型にマッピングするのが難しいため、一旦、String型で扱う
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy/MM/dd")
//	@JsonFormat(pattern = "yyyy/MM/dd")
//	@NotNull
//	private LocalDate order_date;
	private String orderDate;

	/**
	 * 納期
	 */
	@JsonProperty(NoStockOrderDataCsv.DELIVERY_DATE)
	// TODO:直接LocalDate型にマッピングするのが難しいため、一旦、String型で扱う
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy/MM/dd")
//	@JsonFormat(pattern = "yyyy/MM/dd")
//	@NotNull
//	private LocalDate delivery_date;
	private String deliveryDate;

	/**
	 * 納品先住所
	 */
	@JsonProperty(NoStockOrderDataCsv.DELIVERY_ADDRESS)
	@Size(max = 256)
	@NotEmpty
	private String deliveryAddress;

	/**
	 * 注文受付ステータス
	 */
	@JsonProperty(NoStockOrderDataCsv.ORDER_STATUS)
	@Size(min = 2, max = 2)
	@NotNull
	private String orderStatus;

	/**
	 * 明細番号
	 */
	@JsonProperty(NoStockOrderDataCsv.LINE_NO)
	@Min(value = 1)
	@Max(value = 99)
	@NotNull
	private int lineNo;

	/**
	 * SKU
	 */
	@JsonProperty(NoStockOrderDataCsv.PRODUCT_CODE)
	@Size(min = 12, max = 12)
	@NotEmpty
	private String productCode;

	/**
	 * 商品名
	 */
	@JsonProperty(NoStockOrderDataCsv.ITEM_NAME)
	@Size(max = 64)
	@NotEmpty
	private String itemName;

	/**
	 * 分類コード
	 */
	@JsonProperty(NoStockOrderDataCsv.CLASSIFICATION_CODE)
	@Size(min = 4, max = 4)
	@NotEmpty
	private String classificationCode;

	/**
	 * 分類名
	 */
	@JsonProperty(NoStockOrderDataCsv.CLASSIFICATION_NAME)
	@Size(max = 64)
	@NotEmpty
	private String classificationName;

	/**
	 * シーズンコード
	 */
	@JsonProperty(NoStockOrderDataCsv.SEASON_CODE)
	@Size(min = 3, max = 3)
	@NotEmpty
	private String seasonCode;

	/**
	 * シーズン名
	 */
	@JsonProperty(NoStockOrderDataCsv.SEASON_NAME)
	@Size(max = 64)
	@NotEmpty
	private String seasonName;

	/**
	 * カラーコード
	 */
	@JsonProperty(NoStockOrderDataCsv.COLOR_CODE)
	@Size(min = 2, max = 2)
	@NotEmpty
	private String colorCode;

	/**
	 * カラー名
	 */
	@JsonProperty(NoStockOrderDataCsv.COLOR_NAME)
	@Size(max = 64)
	@NotEmpty
	private String colorName;

	/**
	 * サイズコード
	 */
	@JsonProperty(NoStockOrderDataCsv.SIZE_CODE)
	@Size(min = 2, max = 2)
	@NotEmpty
	private String sizeCode;

	/**
	 * サイズ名
	 */
	@JsonProperty(NoStockOrderDataCsv.SIZE_NAME)
	@Size(max = 64)
	@NotEmpty
	private String sizeName;

	/**
	 * 数量
	 */
	@JsonProperty(NoStockOrderDataCsv.QUANTITY)
	@Max(value = 999)
	@NotNull
	private BigDecimal quantity;

	/**
	 * 販売価格（税抜）
	 */
	@JsonProperty(NoStockOrderDataCsv.SELLING_PRICE)
	@Max(value = 9999999999L)
	@NotNull
	private BigDecimal sellingPrice;

	/**
	 * 販売価格（税込）
	 */
	@JsonProperty(NoStockOrderDataCsv.SELLING_PRICE_INTAX)
	@Max(value = 9999999999L)
	@NotNull
	private BigDecimal sellingPriceIntax;

	/**
	 * 注文明細ステータス
	 */
	@JsonProperty(NoStockOrderDataCsv.ORDER_LINE_STATUS)
	@Size(min = 2, max = 2)
	@NotEmpty
	private String orderLineStatus;

	/**
	 * 小計販売価格（税抜）
	 */
	@JsonProperty(NoStockOrderDataCsv.TOTAL_SELLING_PRICE)
	@Max(value = 9999999999L)
	@NotNull
	private BigDecimal totalSellingPrice;

	/**
	 * 小計販売価格（税込）
	 */
	@JsonProperty(NoStockOrderDataCsv.TOTAL_SELLING_PRICE_INTAX)
	@Max(value = 9999999999L)
	@NotNull
	private BigDecimal totalSellingPriceIntax;

}
