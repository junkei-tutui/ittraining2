package jp.zein.it.training.common.generate.dao;

import jp.zein.it.training.common.generate.entity.TblOrderDetailEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

/**
 */
@Dao
public interface TblOrderDetailEntityDao {

    /**
     * @param orderNo
     * @param lineNo
     * @return the TblOrderDetailEntity entity
     */
    @Select
    TblOrderDetailEntity selectById(Integer orderNo, Integer lineNo);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull = true)
    int insert(TblOrderDetailEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(TblOrderDetailEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(TblOrderDetailEntity entity);
}