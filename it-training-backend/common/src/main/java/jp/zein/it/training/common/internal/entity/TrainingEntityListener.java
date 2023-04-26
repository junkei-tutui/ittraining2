package jp.zein.it.training.common.internal.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import org.seasar.doma.jdbc.entity.EntityListener;
import org.seasar.doma.jdbc.entity.PreInsertContext;
import org.seasar.doma.jdbc.entity.PreUpdateContext;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * レコードのINSERT、UPDATE時に登録日時、登録者、更新日時、更新者を自動設定するリスナー
 *
 */
public class TrainingEntityListener<ENTITY> implements EntityListener<ENTITY> {

	private static final String SYSTEM_USER = "system";

	@Override
	public void preInsert(ENTITY entity, PreInsertContext<ENTITY> context) {

		var bwComp = new BeanWrapperImpl(entity);
		try {
			if (Objects.isNull(bwComp.getPropertyValue("createdAt"))) {
				bwComp.setPropertyValue("createdAt", now());
			}
		} catch (BeansException ex) {
			// 例外無視
		}

		try {
			if (Objects.isNull(bwComp.getPropertyValue("createdBy"))) {
				bwComp.setPropertyValue("createdBy", getLoginUserName());
			}
		} catch (BeansException ex) {
			// 例外無視
		}

		try {
			if (Objects.isNull(bwComp.getPropertyValue("IsDelete"))) {
				bwComp.setPropertyValue("IsDelete", true);
			}
		} catch (BeansException ex) {
			// 例外無視
		}

	}

	@Override
	public void preUpdate(ENTITY entity, PreUpdateContext<ENTITY> context) {
		var bwComp = new BeanWrapperImpl(entity);

		try {
			bwComp.setPropertyValue("modifyAt", now());
		} catch (BeansException ex) {
			// 例外無視
		}

		try {
			bwComp.setPropertyValue("modifyBy", getLoginUserName());
		} catch (BeansException ex) {
			// 例外無視
		}
	}

	/**
	 * ログインユーザー名を取得。
	 *
	 * @return ログインユーザー名
	 */
	private String getLoginUserName() {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		if (Objects.isNull(auth)) {
			return SYSTEM_USER;
		}

		return auth.getName();
	}

	/**
	 * 現在日時を取得
	 *
	 * <p>
	 * わざわざDarwinDateTimeServiceをlookupするほどでもないので、{@link LocalDateTime#now()}をそのまま呼び出す
	 * </p>
	 *
	 * @return 現在日時
	 */
	private LocalDateTime now() {
		return LocalDateTime.now();
	}

}
