package jp.zein.it.training.was.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.criteria.Entityql;

import jp.zein.it.training.common.generate.entity.MstItemEntity;
import jp.zein.it.training.common.generate.entity.MstItemEntity_;
import jp.zein.it.training.common.generate.entity.MstPartnerEntity;
import jp.zein.it.training.was.entity.mstitementitydao.ItemEntity;

@ConfigAutowireable
@Dao
public interface WasMstItemEntityDao extends jp.zein.it.training.common.dao.CommonMstItemEntityDao {

	/**
	 * 商品情報取得（削除フラグ込みのPK検索）
	 * 
	 * @param itemCode 商品コード
	 * @return {@link MstPartnerEntity}
	 */
	default MstItemEntity selectByIdIncLD(String itemCode) {

		var Entityql = new Entityql(Config.get(this));
		var A = new MstItemEntity_();

		return Entityql.from(A)//
				.where(c -> {
					// 商品マスタ.商品コード ＝ パラメータ.商品コード
					c.eq(A.itemCode, itemCode);
				}).fetchOne();
	}

	/**
	 * 商品一覧取得処理（検索）
	 * 
	 * <p>
	 * 参照先クエリ：
	 * /was/src/main/resources/META-INF/jp/zein/it/training/was/dao/WasMstItemEntityDao/searchItemListByConditions.sql
	 * <p>
	 * 
	 * @param targetClassificationCode 分類コード（検索条件）
	 * @param likeItemName             商品名（検索条件）
	 * @return {@link List<ItemEntity>}
	 */
	@Select
	List<ItemEntity> searchItemListByConditions(String targetClassificationCode, String likeItemName);

	/**
	 * 商品に指定の取引先（仕入先）が存在するかをチェック
	 * 
	 * <p>
	 * 参照先クエリ：
	 * /was/src/main/resources/META-INF/jp/zein/it/training/was/dao/WasMstItemEntityDao/isExistsItemByPartner.sql
	 * <p>
	 * 
	 * @param partnerId 取引先ID
	 * @return 仕入先有無
	 */
	@Select
	Boolean isExistsItemByPartner(String partnerId);

}