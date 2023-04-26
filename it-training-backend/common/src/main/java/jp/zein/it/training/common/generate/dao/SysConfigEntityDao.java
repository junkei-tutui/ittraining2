package jp.zein.it.training.common.generate.dao;

import jp.zein.it.training.common.generate.entity.SysConfigEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

/**
 */
@Dao
public interface SysConfigEntityDao {

    /**
     * @param variable
     * @return the SysConfigEntity entity
     */
    @Select
    SysConfigEntity selectById(String variable);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull = true)
    int insert(SysConfigEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(SysConfigEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(SysConfigEntity entity);
}