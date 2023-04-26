package jp.zein.it.training.common.generate.dao;

import jp.zein.it.training.common.generate.entity.TblOrderEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

/**
 */
@Dao
public interface TblOrderEntityDao {

    /**
     * @param orderNo
     * @return the TblOrderEntity entity
     */
    @Select
    TblOrderEntity selectById(Integer orderNo);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull = true)
    int insert(TblOrderEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(TblOrderEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(TblOrderEntity entity);
}