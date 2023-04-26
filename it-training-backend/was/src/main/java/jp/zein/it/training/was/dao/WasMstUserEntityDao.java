package jp.zein.it.training.was.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.criteria.Entityql;

import jp.zein.it.training.common.generate.entity.MstUserEntity;
import jp.zein.it.training.common.generate.entity.MstUserEntity_;

@ConfigAutowireable
@Dao
public interface WasMstUserEntityDao extends jp.zein.it.training.common.dao.CommonMstUserEntityDao {

	/**
	 * ユーザー情報取得（削除フラグ込みの取得）
	 * 
	 * @param userId ユーザーID
	 * @return {@link MstUserEntity}
	 */
	default MstUserEntity selectByIdIncLD(String userId) {

		var Entityql = new Entityql(Config.get(this));
		var A = new MstUserEntity_();

		//
		return Entityql.from(A)//
				.where(c -> {
					// ユーザーテーブル.ユーザーID ＝ パラメータ.ユーザーID
					c.eq(A.userId, userId);
				}).fetchOne();
	}

	@Select
	List<MstUserEntity> searchUserListByConditions(String likeUserName);

}