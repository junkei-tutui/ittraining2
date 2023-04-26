package jp.zein.it.training.was.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.zein.it.training.was.entity.tblstockcaptureentitydao.UploadedStockEntity;

@ConfigAutowireable
@Dao
public interface WasTblStockCaptureEntityDao extends jp.zein.it.training.common.dao.CommonTblStockCaptureEntityDao {

	/**
	 * 取込在庫ファイルの重複チェック
	 * 
	 * <p>
	 * 参照先クエリ：
	 * /was/src/main/resources/META-INF/jp/zein/it/training/was/dao/WasTblStockCaptureEntityDao/isDuplicatedByFileNameStock.sql
	 * <p>
	 * 
	 * @param filenameStock
	 * @return
	 */
	@Select
	boolean isDuplicatedByFileNameStock(String filenameStock);

	/**
	 * <p>
	 * 参照先クエリ：
	 * /was/src/main/resources/META-INF/jp/zein/it/training/was/dao/WasTblStockCaptureEntityDao/isDuplicatedByFileNameStock.sql
	 * <p>
	 * 
	 * @return
	 */
	@Select
	List<UploadedStockEntity> selectListByAll();
}