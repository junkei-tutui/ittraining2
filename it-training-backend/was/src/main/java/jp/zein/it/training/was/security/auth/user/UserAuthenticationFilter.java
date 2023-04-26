package jp.zein.it.training.was.security.auth.user;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.zein.it.training.was.controller.session.login.LoginRequest;

/**
 * ユーザー画面用認証フィルター。
 *
 * https://fueiseix.hatenablog.com/entry/2018/03/11/191200
 */
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

		// ログイン・リクエストのJSONを読み込む
		LoginRequest loginParameter;
		try {
			loginParameter = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
		} catch (IOException e) {
			loginParameter = new LoginRequest();
		}

		// トークンの作成
		var authRequest = new UserAuthenticationToken(loginParameter.getUserId(), loginParameter.getPassword());

		// 言語をロケールに設定（常に日本語）
		LocaleContextHolder.setLocale(new Locale(Locale.JAPANESE.getLanguage()));

		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

}