package jp.zein.it.training.was.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.criteria.NativeSql;

import jp.zein.it.training.common.generate.entity.TblOrderDetailEntity;
import jp.zein.it.training.common.generate.entity.TblOrderDetailEntity_;
import jp.zein.it.training.was.entity.tblorderdetailentitydao.OrderDetailEntity;
import jp.zein.it.training.was.entity.tblorderdetailentitydao.OrderDetailItemEntity;

@ConfigAutowireable
@Dao
public interface WasTblOrderDetailEntityDao extends jp.zein.it.training.common.dao.CommonTblOrderDetailEntityDao {

	/**
	 * 受注明細一覧取得（画面用）
	 * <p>
	 * 参照先クエリ：
	 * /was/src/main/resources/META-INF/jp/zein/it/training/was/dao/WasTblOrderDetailEntityDao/selectListByOrderNo.sql
	 * <p>
	 * 
	 * @param orderNo 受注番号
	 * @return {@link List<OrderDetailEntity>}
	 */
	@Select
	List<OrderDetailEntity> selectListByOrderNo(int orderNo);

	/**
	 * 受注明細一覧取得
	 * 
	 * <p>
	 * ネイティブSql（O/Rマッピング）による検索
	 * <p>
	 * 
	 * @param orderNo 受注番号
	 * @return {@link List<TblOrderDetailEntity>}
	 */
	default List<TblOrderDetailEntity> selectByOrderNo(int orderNo) {
		var nativeSql = new NativeSql(Config.get(this));
		var A = new TblOrderDetailEntity_();

		return nativeSql.from(A)//
				.where(c -> {
					// 受注明細テーブル.受注番号 ＝ パラメータ.受注番号
					c.eq(A.orderNo, orderNo);
					// 受注明細テーブル.削除フラグ = 0:有効
					c.eq(A.isDelete, false);
				}).select(A)//
				.fetch();
	}

	/**
	 * 受注（受注明細）に指定の商品が存在するかをチェック
	 * 
	 * <p>
	 * 参照先クエリ：
	 * /was/src/main/resources/META-INF/jp/zein/it/training/was/dao/WasTblOrderDetailEntityDao/isExistsOrderDetailByItem.sql
	 * <p>
	 * 
	 * @param itemCode 商品コード
	 * @return 受注商品有無
	 */
	@Select
	Boolean isExistsOrderDetailByItem(String itemCode);

	/**
	 * 受注（受注明細）に指定の商品が存在するかをチェック
	 * 
	 * <p>
	 * 参照先クエリ：
	 * /was/src/main/resources/META-INF/jp/zein/it/training/was/dao/WasTblOrderDetailEntityDao/isExistsOrderDetailByItem.sql
	 * <p>
	 * 
	 * @param itemCode 商品コード
	 * @return 受注商品有無
	 */
	@Select
	Boolean isExistsOrderDetailByDLProduct(String itemCode);

	/**
	 * 受注（受注明細）に指定の商品（SKU）が存在するかをチェック
	 * 
	 * <p>
	 * 参照先クエリ：
	 * /was/src/main/resources/META-INF/jp/zein/it/training/was/dao/WasTblOrderDetailEntityDao/isExistsOrderDetailByItem.sql
	 * <p>
	 * 
	 * @param product_code 商品（SKU）コード
	 * @return 受注商品有無
	 */
	@Select
	Boolean isExistsOrderDetailByProductSku(String productCode);

	/**
	 * 受注詳細情報取得
	 * <p>
	 * 参照先クエリ：
	 * /was/src/main/resources/META-INF/jp/zein/it/training/was/dao/WasTblOrderDetailEntityDao/selectIncludeMasterByOrderNo.sql
	 * <p>
	 * 
	 * @param orderNo 受注番号
	 * @return {@link List<OrderDetailItemEntity>}
	 */
	@Select
	List<OrderDetailItemEntity> selectIncludeMasterByOrderNo(int orderNo);

}