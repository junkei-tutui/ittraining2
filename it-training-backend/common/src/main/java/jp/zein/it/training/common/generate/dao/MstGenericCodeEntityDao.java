package jp.zein.it.training.common.generate.dao;

import jp.zein.it.training.common.generate.entity.MstGenericCodeEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

/**
 */
@Dao
public interface MstGenericCodeEntityDao {

    /**
     * @param genericCodeTypeId
     * @return the MstGenericCodeEntity entity
     */
    @Select
    MstGenericCodeEntity selectById(String genericCodeTypeId);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull = true)
    int insert(MstGenericCodeEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(MstGenericCodeEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(MstGenericCodeEntity entity);
}