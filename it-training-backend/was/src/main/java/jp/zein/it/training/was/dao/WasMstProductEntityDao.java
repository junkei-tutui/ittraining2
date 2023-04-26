package jp.zein.it.training.was.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.criteria.Entityql;
import org.seasar.doma.jdbc.criteria.NativeSql;
import org.seasar.doma.jdbc.criteria.tuple.Tuple3;

import jp.zein.it.training.common.generate.entity.MstColorEntity;
import jp.zein.it.training.common.generate.entity.MstColorEntity_;
import jp.zein.it.training.common.generate.entity.MstProductEntity;
import jp.zein.it.training.common.generate.entity.MstProductEntity_;
import jp.zein.it.training.common.generate.entity.MstSizeEntity;
import jp.zein.it.training.common.generate.entity.MstSizeEntity_;

@ConfigAutowireable
@Dao
public interface WasMstProductEntityDao extends jp.zein.it.training.common.dao.CommonMstProductEntityDao {

	/**
	 * 指定商品コードのSKU情報一覧取得
	 * 
	 * <p>
	 * ネイティブSql（O/Rマッピング）による検索 以下の3テーブルの紐づき情報を取得する。 1.商品マスタ：MstProductEntity
	 * 2.カラーマスタ：MstColorEntity 3.サイズマスタ：MstSizeEntity
	 * <p>
	 * 
	 * @param itemCode 品目コード
	 * @return {@link List<Tuple3<MstProductEntity, MstColorEntity, MstSizeEntity>>}
	 */
	default List<Tuple3<MstProductEntity, MstColorEntity, MstSizeEntity>> getListByItemCode(String itemCode) {
		var nativeSql = new NativeSql(Config.get(this));
		var A = new MstProductEntity_();
		var B = new MstColorEntity_();
		var C = new MstSizeEntity_();

		return nativeSql.from(A)//
				.where(c -> {
					// 商品マスタ.品目コード ＝ パラメータ.品目コード
					c.eq(A.itemCode, itemCode);
					// 商品マスタ.削除フラグ = 0:有効
					c.eq(A.isDelete, false);

					// カラーマスタ.削除フラグ = 0:有効
					c.eq(B.isDelete, false);
					// サイズマスタ.削除フラグ = 0:有効
					c.eq(C.isDelete, false);
				})//
				.innerJoin(B, on -> {
					on.eq(A.colorCode, B.colorCode);
				})//
				.innerJoin(C, on -> {
					on.eq(A.sizeCode, C.sizeCode);
				})//
				.select(A, B, C)//
				.fetch();
	}

	default List<MstProductEntity> selectByItemcode(String itemCode) {
		var Entityql = new Entityql(Config.get(this));
		var A = new MstProductEntity_();

		//
		return Entityql.from(A)//
				.where(c -> {
					// 商品（SKU）マスタ.商品コード ＝ パラメータ.商品コード
					c.eq(A.itemCode, itemCode);
					// 商品（SKU）マスタ.削除フラグ ＝ false
					c.eq(A.isDelete, false);
				}).fetch();
	}

	/**
	 * 指定商品コードのSKU情報一括削除（論理削除）
	 * 
	 * @param itemCode 品目コード
	 * @return
	 */
	default void logicalDeleteByItemcode(String itemCode) {

		var list = selectByItemcode(itemCode);

		var entityql = new Entityql(Config.get(this));
		var s = new MstProductEntity_();
		list.forEach(v -> {
			// 削除フラグをONに設定する
			v.setIsDelete(true);
		});
		entityql.update(s, list).execute();
	}

	/**
	 * 商品（SKU）情報取得（削除フラグ込みの取得）
	 * 
	 * @param productCode 商品（SKU）コード
	 * @return {@link MstProductEntity}
	 */
	default MstProductEntity selectByIdIncLD(String productCode) {

		var Entityql = new Entityql(Config.get(this));
		var A = new MstProductEntity_();

		//
		return Entityql.from(A)//
				.where(c -> {
					// 商品（SKU）マスタ.商品（SKU）コード ＝ パラメータ.商品（SKU）コード
					c.eq(A.productCode, productCode);
				}).fetchOne();
	}

}