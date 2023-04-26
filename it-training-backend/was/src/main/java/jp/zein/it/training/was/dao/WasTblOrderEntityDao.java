package jp.zein.it.training.was.dao;

import java.time.LocalDate;
import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.criteria.NativeSql;
import org.seasar.doma.jdbc.criteria.expression.Expressions;

import jp.zein.it.training.common.generate.entity.TblOrderEntity_;
import jp.zein.it.training.was.entity.tblorderentitydao.OrderHeaderEntity;
import jp.zein.it.training.was.entity.tblorderentitydao.OrderHeaderFullEntity;

@ConfigAutowireable
@Dao
public interface WasTblOrderEntityDao extends jp.zein.it.training.common.dao.CommonTblOrderEntityDao {

	/**
	 * 受注一覧取得（検索）
	 * 
	 * <p>
	 * 参照先クエリ：
	 * /was/src/main/resources/META-INF/jp/zein/it/training/was/dao/WasTblOrderEntityDao/searchOrderListByConditions.sql
	 * <p>
	 * 
	 * @param likeClientName    クライアント名（検索条件）
	 * @param targetOrderStatus 受注ステータス（検索条件）
	 * @param targetOrderDate   受注日（検索条件）
	 * @return {@link List<OrderHeaderEntity>}
	 */
	@Select
	List<OrderHeaderEntity> searchOrderListByConditions(String likeClientName, String targetOrderStatus,
			LocalDate targetOrderDate);

	/**
	 * 受注に指定の取引先（クライアント）が存在するかをチェック
	 * 
	 * <p>
	 * 参照先クエリ：
	 * /was/src/main/resources/META-INF/jp/zein/it/training/was/dao/WasTblOrderEntityDao/isExistsOrderByPartner.sql
	 * <p>
	 * 
	 * @param partnerId 取引先ID
	 * @return 仕入先有無
	 */
	@Select
	Boolean isExistsOrderByPartner(String partnerId);

	/**
	 * 最大受注番号取得
	 * 
	 * @return
	 */
	default int lastOrderNo() {

		var nativeSql = new NativeSql(Config.get(this));
		var o = new TblOrderEntity_();

		var maxNo = nativeSql.from(o).select(Expressions.max(o.orderNo)).fetchOne();
		return maxNo.intValue();
	}

	/**
	 * 確認済みの受注一覧を取得
	 * <p>
	 * 参照先クエリ：
	 * /was/src/main/resources/META-INF/jp/zein/it/training/was/dao/WasTblOrderEntityDao/selectByConfirmOrder.sql
	 * <p>
	 * 
	 * @return
	 */
	@Select
	List<OrderHeaderFullEntity> selectByConfirmOrder();

}