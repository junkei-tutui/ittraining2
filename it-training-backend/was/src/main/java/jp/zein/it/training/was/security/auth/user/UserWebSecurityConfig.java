package jp.zein.it.training.was.security.auth.user;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import jp.zein.it.training.was.controller.common.error.ErrorResponse;
import jp.zein.it.training.was.controller.session.login.LoginResponse;
import jp.zein.it.training.was.exception.TrainingAuthenticationException;
import jp.zein.it.training.was.security.WasSecurityConsts;
import jp.zein.it.training.was.security.auth.WasWebSecurityConfigDelegate;
import jp.zein.it.training.was.session.user.UserSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserWebSecurityConfig implements WasWebSecurityConfigDelegate {

	@Value("${training.security.user.login-uri}")
	private String loginUri;

	@Value("${training.security.user.logout-uri}")
	private String logoutUri;

	private final UserSession userSession;

	private final MappingJackson2HttpMessageConverter httpMessageConverter;

	private final UserAuthenticationProvider authenticationProvider;

	private final LocaleResolver localeResolver;

	private final ModelMapper modelMapper;

//	private final WasAuthorityEntityDao wasAuthorityEntityDao;

	@Override
	public void configure(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

		// ログインURIの設定
		http.authorizeRequests().mvcMatchers(loginUri).permitAll();
		// ログアウトURIの設定
		http.logout().logoutUrl(logoutUri).permitAll().invalidateHttpSession(true)
				.logoutSuccessHandler((request, response, authentication) -> {
					response.setStatus(HttpStatus.OK.value());
				});

		// 認証フィルターを追加
		var filter = new UserAuthenticationFilter();
		filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(loginUri, HttpMethod.POST.name()));
		filter.setAuthenticationManager(authenticationManager);

		// 認証成功時のレスポンス
		filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
			log.info("ログイン成功 userId={}", userSession.getUserId());

//			// ユーザーセッションの言語をロケールに設定
//			localeResolver.setLocale(request, response, new Locale(Locale.JAPANESE.getLanguage()));
//			log.info("ユーザのロケール設定={}", localeResolver.resolveLocale(request).toString());

			response.setStatus(HttpStatus.OK.value());

			// javascript(axios)から参照可能なHTTPレスポンスヘッダー一覧を定義。
			// 参考URL:
			// https://note.kiriukun.com/entry/20200303-axios-response-header-could-not-get
			response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, WasSecurityConsts.AUTH_TOKEN_NAME);

			var body = modelMapper.map(userSession, LoginResponse.class);

			httpMessageConverter.write(body, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
		});

		// 認証失敗時のレスポンス
		filter.setAuthenticationFailureHandler((request, response, exception) -> {
			log.info("ログイン失敗: {}", exception.getMessage());

			response.setStatus(HttpStatus.UNAUTHORIZED.value());

			ErrorResponse error;
			if (exception instanceof TrainingAuthenticationException) {
				var ex = (TrainingAuthenticationException) exception;
				error = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
			} else {
				error = new ErrorResponse("auth error", "IDもしくはパスワードが異なります。");
			}

			httpMessageConverter.write(error, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
		});

		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

	@Override
	public List<String> getRoleHierarchyList() {
		// 今回は固定でTESTを返す
		return Arrays.asList("TEST");
	}

}