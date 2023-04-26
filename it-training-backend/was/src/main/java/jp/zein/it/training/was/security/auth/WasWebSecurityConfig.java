package jp.zein.it.training.was.security.auth;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jp.zein.it.training.was.controller.common.error.ErrorResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WasWebSecurityConfig extends WebSecurityConfigurerAdapter {
	/**
	 * CORS対象のパス。
	 */
	private static final String ALL_PATH = "/**";

	@Value("${training.security.permit-all-uris}")
	private String[] permitAllUris;

	@Value("${training.security.permit-all-rest-uris}")
	private String[] permitAllRestUris;

	@Value("${training.security.cors-allowed-origin}")
	private String[] corsAllowedOrigins;

	private final MappingJackson2HttpMessageConverter httpMessageConverter;

	private final List<WasWebSecurityConfigDelegate> configurerList;

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 認証対象外URIを設定
		// web.ignoring().antMatchers(ignoreUris);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// 移譲先を呼び出す
		for (var configurer : configurerList) {
			configurer.configure(http, authenticationManager());
		}

		// POST時の「Invalid CSRF token」を抑止
		http.csrf().disable();

		// 認証要否のURIを定義
		http.authorizeRequests()//
				// 未認証でアクセス可能URI
				.mvcMatchers(permitAllRestUris).permitAll() //
				.antMatchers(permitAllUris).permitAll() //

				// その他は認証必須
				.anyRequest().authenticated()

				.and() //

				// 例外ハンドリング
				.exceptionHandling()
				// 未認証の場合、エラーレスポンス
				.authenticationEntryPoint((request, response, authException) -> errorResponse(response,
						HttpStatus.UNAUTHORIZED, "認証エラーが発生しました。"))
				// 権限なしの場合、エラーレスポンス
				.accessDeniedHandler((request, response, authException) -> errorResponse(response, HttpStatus.FORBIDDEN,
						"認可エラーが発生しました。"));

		// CORS設定
		http.cors().configurationSource(getCorsConfigurationSource());

	}

	/**
	 * 指定されたHTTPステータスコードでエラー電文をレスポンスする
	 *
	 * @param response     {@link HttpServletResponse}
	 * @param httpStatus   HTTPステータス
	 * @param errorMessage エラーメッセージ
	 * @throws HttpMessageNotWritableException
	 * @throws IOException
	 */
	private void errorResponse(HttpServletResponse response, HttpStatus httpStatus, String errorMessage)
			throws HttpMessageNotWritableException, IOException {
		response.setStatus(httpStatus.value());

		var error = new ErrorResponse(httpStatus.name(), errorMessage);
		httpMessageConverter.write(error, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
	}

	/**
	 * CORS設定の取得
	 *
	 * @return {@link CorsConfigurationSource}
	 */
	private CorsConfigurationSource getCorsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.setAllowCredentials(true);
		// 全てのメソッドを許可
		corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
		// 全てのヘッダを許可
		corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
		// 許可オリジンを設定
		for (var origin : corsAllowedOrigins) {
			// corsConfiguration.addAllowedOrigin(origin);
			corsConfiguration.addAllowedOriginPattern(origin);
		}

		UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
		// パスごとに設定が可能。ここでは全てのパスに対して設定
		corsSource.registerCorsConfiguration(ALL_PATH, corsConfiguration);

		return corsSource;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 移譲先を呼び出す
		for (var configurer : configurerList) {
			configurer.configure(auth);
		}
	}

	@Bean
	public RoleHierarchyVoter roleHierarchyVoter() {
		return new RoleHierarchyVoter(roleHierarchy());
	}

	@Bean
	public RoleHierarchy roleHierarchy() {
		final RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

		hierarchy.setHierarchy(//
				configurerList.stream()//
						.map(WasWebSecurityConfigDelegate::getRoleHierarchyList).flatMap(List::stream)
						.collect(Collectors.joining("\n")));

		return hierarchy;
	}

}
