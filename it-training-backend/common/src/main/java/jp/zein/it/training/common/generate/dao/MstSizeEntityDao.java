package jp.zein.it.training.common.generate.dao;

import jp.zein.it.training.common.generate.entity.MstSizeEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

/**
 */
@Dao
public interface MstSizeEntityDao {

    /**
     * @param sizeCode
     * @return the MstSizeEntity entity
     */
    @Select
    MstSizeEntity selectById(String sizeCode);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull = true)
    int insert(MstSizeEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(MstSizeEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(MstSizeEntity entity);
}