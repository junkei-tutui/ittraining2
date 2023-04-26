package jp.zein.it.training.was.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.zein.it.training.common.generate.entity.MstColorEntity;

@ConfigAutowireable
@Dao
public interface WasMstColorEntityDao extends jp.zein.it.training.common.dao.CommonMstColorEntityDao {

	/**
	 * カラーコード一覧取得
	 * 
	 * <p>
	 * 参照先クエリ：
	 * /was/src/main/resources/META-INF/jp/zein/it/training/was/dao/WasMstColorEntityDao/selectList.sql
	 * <p>
	 * 
	 * @return {@link List<MstColorEntity>}
	 */
	@Select
	List<MstColorEntity> selectList();

}