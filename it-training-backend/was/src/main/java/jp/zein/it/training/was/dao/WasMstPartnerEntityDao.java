package jp.zein.it.training.was.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.criteria.Entityql;

import jp.zein.it.training.common.generate.entity.MstPartnerEntity;
import jp.zein.it.training.common.generate.entity.MstPartnerEntity_;

@ConfigAutowireable
@Dao
public interface WasMstPartnerEntityDao extends jp.zein.it.training.common.dao.CommonMstPartnerEntityDao {

	/**
	 * 取引先情報取得（削除フラグ込みの取得）
	 * 
	 * @param partnerId 取引先ID
	 * @return {@link MstPartnerEntity}
	 */
	default MstPartnerEntity selectByIdIncLD(String partnerId) {

		var Entityql = new Entityql(Config.get(this));
		var A = new MstPartnerEntity_();

		//
		return Entityql.from(A)//
				.where(c -> {
					// 取引先テーブル.取引先ID ＝ パラメータ.取引先ID
					c.eq(A.partnerId, partnerId);
				}).fetchOne();
	}

	/**
	 * 取引先一覧取得（検索）
	 * 
	 * <p>
	 * 参照先クエリ：
	 * /was/src/main/resources/META-INF/jp/zein/it/training/was/dao/WasMstPartnerEntityDao/searchPartnerListByConditions.sql
	 * <p>
	 * 
	 * @param likePartnerName 取引先名（検索条件）
	 * @return {@link List<MstPartnerEntity>}
	 */
	@Select
	List<MstPartnerEntity> searchPartnerListByConditions(String likePartnerName);

	/**
	 * 仕入先一覧取得
	 * 
	 * <p>
	 * 参照先クエリ：
	 * /was/src/main/resources/META-INF/jp/zein/it/training/was/dao/WasMstPartnerEntityDao/getSupplierList.sql
	 * <p>
	 * 
	 * @return {@link List<MstPartnerEntity>}
	 */
	@Select
	List<MstPartnerEntity> getSupplierList();

}