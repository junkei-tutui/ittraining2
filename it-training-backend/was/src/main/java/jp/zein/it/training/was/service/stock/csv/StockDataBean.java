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
 * 在庫ファイル（CSVデータ）Bean
 * <p>
 * 在庫引き当て後の結果ファイルと兼用
 * <p>
 */
@Data
@JsonPropertyOrder({ "line_no", "category_code", "category_name", "item_code", "product_code", "item_name",
		"partner_name", "classification_code", "classification_name", "ref_no", "season_code", "season_name",
		"color_code", "color_name", "size_code", "size_name", "logocal_qty", "allocable_qty", "reserved_qty",
		"shipping_qty" })
public class StockDataBean implements CsvBean {

	/**
	 * 在庫ファイルCSVの定義
	 */
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class StockDataCsv {
		// --------------------------------------------------------------------
		// カラム定義
		// --------------------------------------------------------------------
		/**
		 * #
		 */
		public static final String LINE_NO = "line_no";

		/**
		 * カテゴリ
		 */
		public static final String CATEGORY_CODE = "category_code";

		/**
		 * カテゴリ名
		 */
		public static final String CATEGORY_NAME = "category_name";

		/**
		 * 商品コード
		 */
		public static final String ITEM_CODE = "item_code";

		/**
		 * SKU
		 */
		public static final String PRODUCT_CODE = "product_code";

		/**
		 * 商品名
		 */
		public static final String ITEM_NAME = "item_name";

		/**
		 * 仕入れ先
		 */
		public static final String PARTNER_NAME = "partner_name";

		/**
		 * 分類コード
		 */
		public static final String CLASSIFICATION_CODE = "classification_code";

		/**
		 * 分類名
		 */
		public static final String CLASSIFICATION_NAME = "classification_name";

		/**
		 * REF NO
		 */
		public static final String REF_NO = "ref_no";

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
		 * 論理在庫
		 */
		public static final String LOGICAL_QTY = "logocal_qty";

		/**
		 * 引当可能在庫
		 */
		public static final String ALLOCABLE_QTY = "allocable_qty";

		/**
		 * 引当済在庫
		 */
		public static final String RESERVED_QTY = "reserved_qty";

		/**
		 * 出荷指示在庫
		 */
		public static final String SHIPPING_QTY = "shipping_qty";
	}

	/**
	 * #
	 */
	@JsonProperty(StockDataCsv.LINE_NO)
	@Min(value = 1)
	@NotNull
	private int line_no;

	/**
	 * カテゴリ
	 */
	@JsonProperty(StockDataCsv.CATEGORY_CODE)
	@Size(min = 4, max = 4)
	@NotEmpty
	private String category_code;

	/**
	 * カテゴリ名
	 */
	@JsonProperty(StockDataCsv.CATEGORY_NAME)
	@Size(max = 64)
	@NotEmpty
	private String category_name;

	/**
	 * 商品コード
	 */
	@JsonProperty(StockDataCsv.ITEM_CODE)
	@Size(min = 8, max = 8)
	@NotEmpty
	private String item_code;

	/**
	 * SKU
	 */
	@JsonProperty(StockDataCsv.PRODUCT_CODE)
	@Size(min = 12, max = 12)
	@NotEmpty
	private String product_code;

	/**
	 * 商品名
	 */
	@JsonProperty(StockDataCsv.ITEM_NAME)
	@Size(max = 64)
	@NotEmpty
	private String item_name;

	/**
	 * 仕入れ先
	 */
	@JsonProperty(StockDataCsv.PARTNER_NAME)
	@Size(max = 64)
	@NotEmpty
	private String partner_name;

	/**
	 * 分類コード
	 */
	@JsonProperty(StockDataCsv.CLASSIFICATION_CODE)
	@Size(min = 2, max = 2)
	@NotEmpty
	private String classification_code;

	/**
	 * 分類名
	 */
	@JsonProperty(StockDataCsv.CLASSIFICATION_NAME)
	@Size(max = 64)
	@NotEmpty
	private String classification_name;

	/**
	 * REF NO
	 */
	@JsonProperty(StockDataCsv.REF_NO)
	@Size(min = 3, max = 3)
	@NotEmpty
	private String ref_no;

	/**
	 * シーズンコード
	 */
	@JsonProperty(StockDataCsv.SEASON_CODE)
	@Size(min = 3, max = 3)
	@NotEmpty
	private String season_code;

	/**
	 * シーズン名
	 */
	@JsonProperty(StockDataCsv.SEASON_NAME)
	@Size(max = 64)
	@NotEmpty
	private String season_name;

	/**
	 * カラーコード
	 */
	@JsonProperty(StockDataCsv.COLOR_CODE)
	@Size(min = 2, max = 2)
	@NotEmpty
	private String color_code;

	/**
	 * カラー名
	 */
	@JsonProperty(StockDataCsv.COLOR_NAME)
	@Size(max = 64)
	@NotEmpty
	private String color_name;

	/**
	 * サイズコード
	 */
	@JsonProperty(StockDataCsv.SIZE_CODE)
	@Size(min = 2, max = 2)
	@NotEmpty
	private String size_code;

	/**
	 * サイズ名
	 */
	@JsonProperty(StockDataCsv.SIZE_NAME)
	@Size(max = 64)
	@NotEmpty
	private String size_name;

	/**
	 * 論理在庫
	 */
	@JsonProperty(StockDataCsv.LOGICAL_QTY)
	@Max(value = 99999)
	@NotNull
	private BigDecimal logical_qty;

	/**
	 * 引当可能在庫
	 */
	@JsonProperty(StockDataCsv.ALLOCABLE_QTY)
	@Max(value = 99999)
	@NotNull
	private BigDecimal allocable_qty;

	/**
	 * 引当済在庫
	 */
	@JsonProperty(StockDataCsv.RESERVED_QTY)
	@Max(value = 99999)
	@NotNull
	private BigDecimal reserved_qty;

	/**
	 * 出荷指示在庫
	 */
	@JsonProperty(StockDataCsv.SHIPPING_QTY)
	@Max(value = 99999)
	@NotNull
	private BigDecimal shipping_qty;
}
