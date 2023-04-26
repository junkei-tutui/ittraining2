package jp.zein.it.training.was.security.auth.user;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * ユーザー画面用{@link UsernamePasswordAuthenticationToken}
 *
 */
public class UserAuthenticationToken extends UsernamePasswordAuthenticationToken {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3711543173435282061L;

	public UserAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

}
