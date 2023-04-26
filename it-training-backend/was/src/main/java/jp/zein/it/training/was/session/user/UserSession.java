package jp.zein.it.training.was.session.user;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

/**
 * ユーザ画面セッション情報
 *
 */
@Data
@SessionScope
@Component
public class UserSession implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ユーザーID
	 */
	private String userId;

	/**
	 * ユーザー名
	 */
	private String userName;

}
