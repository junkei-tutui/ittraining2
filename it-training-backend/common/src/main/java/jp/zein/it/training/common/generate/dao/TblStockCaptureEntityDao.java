package jp.zein.it.training.common.generate.dao;

import jp.zein.it.training.common.generate.entity.TblStockCaptureEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

/**
 */
@Dao
public interface TblStockCaptureEntityDao {

    /**
     * @param stockCaptureId
     * @return the TblStockCaptureEntity entity
     */
    @Select
    TblStockCaptureEntity selectById(Long stockCaptureId);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull = true)
    int insert(TblStockCaptureEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(TblStockCaptureEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(TblStockCaptureEntity entity);
}