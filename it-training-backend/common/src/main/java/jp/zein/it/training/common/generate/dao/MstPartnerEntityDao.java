package jp.zein.it.training.common.generate.dao;

import jp.zein.it.training.common.generate.entity.MstPartnerEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

/**
 */
@Dao
public interface MstPartnerEntityDao {

    /**
     * @param partnerId
     * @return the MstPartnerEntity entity
     */
    @Select
    MstPartnerEntity selectById(String partnerId);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull = true)
    int insert(MstPartnerEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(MstPartnerEntity entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(MstPartnerEntity entity);
}