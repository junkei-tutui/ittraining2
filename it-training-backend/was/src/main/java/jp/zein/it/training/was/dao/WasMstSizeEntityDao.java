package jp.zein.it.training.was.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.zein.it.training.common.generate.entity.MstSizeEntity;

@ConfigAutowireable
@Dao
public interface WasMstSizeEntityDao extends jp.zein.it.training.common.dao.CommonMstSizeEntityDao {

	/**
	 * サイズコード一覧取得
	 * 
	 * <p>
	 * 参照先クエリ：
	 * /was/src/main/resources/META-INF/jp/zein/it/training/was/dao/WasMstSizeEntityDao/selectList.sql
	 * <p>
	 * 
	 * @return {@link List<MstSizeEntity>}
	 */
	@Select
	List<MstSizeEntity> selectList();

}