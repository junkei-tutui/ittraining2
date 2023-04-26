package jp.zein.it.training.was.entity.tblorderdetailentitydao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import jp.zein.it.training.common.generate.entity.MstItemEntity;
import jp.zein.it.training.common.generate.entity.TblOrderDetailEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 受注詳細情報取得用エンティティ
 * <p>
 * 受注明細{@link TblOrderDetailEntity}の名称および<br />
 * 商品マスタ{@link MstItemEntity}の情報を追加
 * <p>
 */
@Entity
@Getter
@Setter
public class OrderDetailItemEntity {

	// 受注明細{@link TblOrderDetailEntity}の全項目
	/** 受注番号 */
	@Column(name = "order_no")
	Integer orderNo;

	/** 明細番号 */
	@Column(name = "line_no")
	Integer lineNo;

	/** SKUコード */
	@Column(name = "product_code")
	String productCode;

	/** 数量 */
	@Column(name = "quantity")
	BigDecimal quantity;

	/** 販売価格（税抜） */
	@Column(name = "selling_price")
	BigDecimal sellingPrice;

	/** 販売価格（税込） */
	@Column(name = "selling_price_intax")
	BigDecimal sellingPriceIntax;

	/** 受注明細ステータス */
	@Column(name = "order_line_status")
	String orderLineStatus;

	/** 小計販売価格（税抜） */
	@Column(name = "subtotal_selling_price")
	BigDecimal subtotalSellingPrice;

	/** 小計販売価格（税込） */
	@Column(name = "subtotal_selling_price_intax")
	BigDecimal subtotalSellingPriceIntax;

	/** 削除フラグ */
	@Column(name = "is_delete")
	Boolean isDelete;

	/** 登録日時 */
	@Column(name = "created_at")
	LocalDateTime createdAt;

	/** 登録者 */
	@Column(name = "created_by")
	String createdBy;

	/** 更新日時 */
	@Column(name = "modify_at")
	LocalDateTime modifyAt;

	/** 更新者 */
	@Column(name = "modify_by")
	String modifyBy;

	// 受注明細{@link TblOrderDetailEntity}からの名称追加分
	/** 品目コード */
	@Column(name = "item_code")
	String itemCode;

	/** 品目名 */
	@Column(name = "item_name")
	String itemName;

	/** カラーコード */
	@Column(name = "color_code")
	String colorCode;

	/** カラー名 */
	@Column(name = "color_name")
	String colorName;

	/** サイズコード */
	@Column(name = "size_code")
	String sizeCode;

	/** サイズ名 */
	@Column(name = "size_name")
	String sizeName;

	// 商品マスタ{@link MstItemEntity}＋名称追加
	/** カテゴリコード */
	@Column(name = "category_code")
	String categoryCode;

	/** カテゴリ名 */
	@Column(name = "category_name")
	String categoryName;

	/** 分類コード */
	@Column(name = "classification_code")
	String classificationCode;

	/** 分類名 */
	@Column(name = "classification_name")
	String classificationName;

	/** シーズンコード */
	@Column(name = "season_code")
	String seasonCode;

	/** シーズン名 */
	@Column(name = "season_name")
	String seasonName;

}
