package jp.zein.it.training.was.service.order.csv;

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
 * 注文書ファイル（CSVデータ）Bean
 * 
 */
@Data
@JsonPropertyOrder({ "client_ID", "order_No", "order_date", "delivery_date", "order_amount", "delivery_zip",
		"delivery_destination", "responsible_party", "order_subject", "Item_No", "item_code", "item_name", "color_code",
		"size_code", "order_quantity", "price", "unit_price", "memo" })
public class OrderDataBean implements CsvBean {

	/**
	 * 注文書ファイルCSVの定義
	 */
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class OrderDataCsv {
		// --------------------------------------------------------------------
		// カラム定義
		// --------------------------------------------------------------------
		/**
		 * 取引先ID
		 */
		public static final String CLIENT_ID = "client_ID";

		/**
		 * 注文番号
		 */
		public static final String ORDER_NO = "order_No";

		/**
		 * 注文日
		 */
		public static final String ORDER_DATE = "order_date";

		/**
		 * 納期
		 */
		public static final String DELIVERY_DATE = "delivery_date";

		/**
		 * 注文金額
		 */
		public static final String ORDER_AMOUNT = "order_amount";

		/**
		 * 納品先（郵便番号）
		 */
		public static final String DELIVERY_ZIP = "delivery_zip";

		/**
		 * 納品先
		 */
		public static final String DELIVERY_DESTINATION = "delivery_destination";

		/**
		 * 担当者
		 */
		public static final String RESPONSIBLE_PARTY = "responsible_party";

		/**
		 * 注文件名
		 */
		public static final String ORDER_SUBJECT = "order_subject";

		/**
		 * 明細No
		 */
		public static final String ITEM_NO = "Item_No";

		/**
		 * 商品コード
		 */
		public static final String ITEM_CODE = "item_code";

		/**
		 * 商品名
		 */
		public static final String ITEM_NAME = "item_name";

		/**
		 * カラーコード
		 */
		public static final String COLOR_CODE = "color_code";

		/**
		 * サイズコード
		 */
		public static final String SIZE_CODE = "size_code";

		/**
		 * 注文数
		 */
		public static final String ORDER_QUANTITY = "order_quantity";

		/**
		 * 価格(税抜)
		 */
		public static final String PRICE = "price";

		/**
		 * 小計(税抜)
		 */
		public static final String UNIT_PRICE = "unit_price";

		/**
		 * 備考
		 */
		public static final String MEMO = "memo";
	}

	/**
	 * 取引先ID
	 */
	@JsonProperty(OrderDataCsv.CLIENT_ID)
	@Size(max = 5, min = 5)
	@NotEmpty
	private String clientId;

	/**
	 * 注文番号（クライアント側）
	 */
	@JsonProperty(OrderDataCsv.ORDER_NO)
	@Size(min = 2, max = 2)
	@NotEmpty
	private String clientOrderNo;

	/**
	 * 注文日
	 */
	@JsonProperty(OrderDataCsv.ORDER_DATE)
	// TODO:直接LocalDate型にマッピングするのが難しいため、一旦、String型で扱う
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy/MM/dd")
//	@JsonFormat(pattern = "yyyy/MM/dd")
//	@NotNull
//	private LocalDate orderDate;
	@NotEmpty
	private String orderDate;

	/**
	 * 納期
	 */
	@JsonProperty(OrderDataCsv.DELIVERY_DATE)
	// TODO:直接LocalDate型にマッピングするのが難しいため、一旦、String型で扱う
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy/MM/dd")
//	@JsonFormat(pattern = "yyyy/MM/dd")
//	@NotNull
//	private LocalDate deliveryDate;
	@NotEmpty
	private String deliveryDate;

	/**
	 * 注文金額（合計注文金額）
	 */
	@JsonProperty(OrderDataCsv.ORDER_AMOUNT)
	@Max(value = 9999999999L)
	@NotNull
	private BigDecimal totalOrderAmount;

	/**
	 * 納品先（郵便番号）
	 */
	@JsonProperty(OrderDataCsv.DELIVERY_ZIP)
	@Size(min = 7, max = 7)
	@NotEmpty
	private String deliveryZip;

	/**
	 * 納品先
	 */
	@JsonProperty(OrderDataCsv.DELIVERY_DESTINATION)
	@Size(max = 200)
	@NotEmpty
	private String deliveryAddress;

	/**
	 * 担当者
	 */
	@JsonProperty(OrderDataCsv.RESPONSIBLE_PARTY)
	@Size(max = 20)
	@NotEmpty
	private String responsibleParty;

	/**
	 * 注文件名
	 */
	@JsonProperty(OrderDataCsv.ORDER_SUBJECT)
	@Size(max = 100)
	@NotEmpty
	private String orderSubject;

	/**
	 * 明細No
	 */
	@JsonProperty(OrderDataCsv.ITEM_NO)
	@Min(value = 1)
	@Max(value = 99)
	@NotNull
	private int lineNo;

	/**
	 * 商品コード
	 */
	@JsonProperty(OrderDataCsv.ITEM_CODE)
	@Size(min = 8, max = 8)
	@NotEmpty
	private String itemCode;

	/**
	 * 商品名
	 */
	@JsonProperty(OrderDataCsv.ITEM_NAME)
	@Size(max = 100)
	@NotEmpty
	private String itemName;

	/**
	 * カラーコード
	 */
	@JsonProperty(OrderDataCsv.COLOR_CODE)
	@Size(min = 2, max = 2)
	@NotEmpty
	private String colorCode;

	/**
	 * サイズコード
	 */
	@JsonProperty(OrderDataCsv.SIZE_CODE)
	@Size(min = 2, max = 2)
	@NotEmpty
	private String sizeCode;

	/**
	 * 注文数
	 */
	@JsonProperty(OrderDataCsv.ORDER_QUANTITY)
	@Max(value = 999)
	@NotNull
	private BigDecimal quantity;

	/**
	 * 価格(税抜)
	 */
	@JsonProperty(OrderDataCsv.PRICE)
	@Max(value = 9999999999L)
	@NotNull
	private BigDecimal sellingPrice;

	/**
	 * 小計(税抜)
	 */
	@JsonProperty(OrderDataCsv.UNIT_PRICE)
	@Max(value = 9999999999L)
	@NotNull
	private BigDecimal subtotalSellingPrice;

	/**
	 * 備考
	 */
	@JsonProperty(OrderDataCsv.MEMO)
	@Size(max = 500)
	private String memo;

}
