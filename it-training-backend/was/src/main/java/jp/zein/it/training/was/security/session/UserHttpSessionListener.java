package jp.zein.it.training.was.security.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class UserHttpSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		var session = event.getSession();
		log.debug("セッションを作成しました :ID=" + session.getId() + " for " + session.getMaxInactiveInterval());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		var session = event.getSession();
		log.debug("以下のセッションをログアウト :ID=" + session.getId());
	}
}
