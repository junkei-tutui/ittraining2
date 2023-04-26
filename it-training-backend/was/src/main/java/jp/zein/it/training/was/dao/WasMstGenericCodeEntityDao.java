package jp.zein.it.training.was.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.zein.it.training.common.generate.entity.MstGenericCodeDetailEntity;

@ConfigAutowireable
@Dao
public interface WasMstGenericCodeEntityDao extends jp.zein.it.training.common.dao.CommonMstGenericCodeEntityDao {

	/**
	 * 指定コード種別の汎用コード値一覧取得
	 * 
	 * <p>
	 * 参照先クエリ：
	 * /was/src/main/resources/META-INF/jp/zein/it/training/was/dao/WasMstGenericCodeEntityDao/selectListByGenericCodeTypeId.sql
	 * <p>
	 * 
	 * @param genericCodeTypeId 汎用コート種別
	 * @return {@link List<MstGenericCodeDetailEntity>}
	 */
	@Select
	List<MstGenericCodeDetailEntity> selectListByGenericCodeTypeId(String genericCodeTypeId);

}