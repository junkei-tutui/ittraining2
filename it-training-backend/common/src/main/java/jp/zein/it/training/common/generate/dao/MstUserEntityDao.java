package jp.zein.it.training.common.generate.dao;

import jp.zein.it.training.common.generate.entity.MstUserEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

/**
 */
@Dao
public interface MstUserEntityDao {

    /**
     * @param userId
     * @return the MstUserEntity entity
     */
    @Select
    MstUserEntity selectById(String userId);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull = true)
    int insert(MstUserEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(MstUserEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(MstUserEntity entity);
}