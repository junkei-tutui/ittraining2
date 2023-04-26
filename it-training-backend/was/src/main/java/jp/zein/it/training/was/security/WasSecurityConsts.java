package jp.zein.it.training.was.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * セキュリティ関連の定数定義
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WasSecurityConsts {

	/**
	 * ロール名prefix
	 */
	public static final String ROLE_NAME_PREFIX = "ROLE_";

	/**
	 * 権限階層
	 */
	public static final String HIERARCHY = " > ";

	/**
	 * 認証トークン・キー名
	 */
	public static final String AUTH_TOKEN_NAME = "X-Auth-Token";

	/**
	 * QueryString部の認証トークン名。
	 */
	public static final String QUERY_STRING_TOKEN = "token";
}
