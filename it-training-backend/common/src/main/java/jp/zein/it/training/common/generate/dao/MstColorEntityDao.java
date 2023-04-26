package jp.zein.it.training.common.generate.dao;

import jp.zein.it.training.common.generate.entity.MstColorEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

/**
 */
@Dao
public interface MstColorEntityDao {

    /**
     * @param colorCode
     * @return the MstColorEntity entity
     */
    @Select
    MstColorEntity selectById(String colorCode);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull = true)
    int insert(MstColorEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(MstColorEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(MstColorEntity entity);
}