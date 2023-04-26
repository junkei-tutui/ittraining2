package jp.zein.it.training.common.generate.dao;

import jp.zein.it.training.common.generate.entity.MstGenericCodeDetailEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

/**
 */
@Dao
public interface MstGenericCodeDetailEntityDao {

    /**
     * @param genericCodeTypeId
     * @param genericCode
     * @return the MstGenericCodeDetailEntity entity
     */
    @Select
    MstGenericCodeDetailEntity selectById(String genericCodeTypeId, String genericCode);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull = true)
    int insert(MstGenericCodeDetailEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(MstGenericCodeDetailEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(MstGenericCodeDetailEntity entity);
}