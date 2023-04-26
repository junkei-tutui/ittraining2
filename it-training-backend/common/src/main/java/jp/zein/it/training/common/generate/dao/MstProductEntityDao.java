package jp.zein.it.training.common.generate.dao;

import jp.zein.it.training.common.generate.entity.MstProductEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

/**
 */
@Dao
public interface MstProductEntityDao {

    /**
     * @param productCode
     * @return the MstProductEntity entity
     */
    @Select
    MstProductEntity selectById(String productCode);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull = true)
    int insert(MstProductEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(MstProductEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(MstProductEntity entity);
}