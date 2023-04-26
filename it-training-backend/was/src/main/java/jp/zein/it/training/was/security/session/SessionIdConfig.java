package jp.zein.it.training.was.security.session;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * セッション設定
 *
 * <p>
 * １つのWebブラウザでタブを分けてユーザ画面と管理者画面を利用できるようにするため、セッションIDをHTTPヘッダーで受け渡す。
 * </p>
 *
 */
//@Traning セッションIDをCookieで連携するように変更（ヘッダー連携をやめる）
//@Configuration
//@EnableSpringHttpSession
//@ConditionalOnProperty(name = "training.test.security.cookie-auth-token", havingValue = "false", matchIfMissing = true)
public class SessionIdConfig {

	/**
	 * {@link EnableSpringHttpSession}を利用するために必要なのでBean定義
	 *
	 * @return {@link SessionRepository}
	 */
	@Bean
	@ConditionalOnMissingBean
	public SessionRepository<?> sessionRepository() {
		return new MapSessionRepository(new ConcurrentHashMap<>());
	}

	/**
	 * セッションIDをHTTPヘッダーで受け渡す
	 *
	 * @return {@link HttpSessionIdResolver}
	 */
	@Bean
	public HttpSessionIdResolver httpSessionIdResolver() {
		return new HeaderAndQueryStringHttpSessionIdResolver();
	}
}
