package jp.zein.it.training.was.security.auth;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * セキュリティ設定の委譲インターフェース
 *
 */
public interface WasWebSecurityConfigDelegate {
	/**
	 * {@link HttpSecurity} を構成。
	 *
	 * @param http                  {@link HttpSecurity}
	 * @param authenticationManager {@link AuthenticationManager}
	 * @throws Exception
	 */
	void configure(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception;

	/**
	 * {@link AuthenticationManagerBuilder}を構成。
	 *
	 * @param auth {@link AuthenticationManagerBuilder}
	 * @throws Exception
	 */
	void configure(AuthenticationManagerBuilder auth) throws Exception;

	/**
	 * 認可ロールの継承関係のリストを取得
	 *
	 * @return 認可ロールの継承関係のリスト
	 */
	List<String> getRoleHierarchyList();
}
