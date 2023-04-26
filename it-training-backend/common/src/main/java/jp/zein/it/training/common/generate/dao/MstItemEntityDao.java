package jp.zein.it.training.common.generate.dao;

import jp.zein.it.training.common.generate.entity.MstItemEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

/**
 */
@Dao
public interface MstItemEntityDao {

    /**
     * @param itemCode
     * @return the MstItemEntity entity
     */
    @Select
    MstItemEntity selectById(String itemCode);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull = true)
    int insert(MstItemEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(MstItemEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(MstItemEntity entity);
}