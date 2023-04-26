package jp.zein.it.training.was.controller.session.login;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.session.user.UserSession;
import lombok.RequiredArgsConstructor;

/**
 * B011：ログインコントローラー
 *
 */
@RestController
@RequiredArgsConstructor
public class LoginController {

	private final UserSession userSession;

	private final ModelMapper modelMapper;

	/**
	 * B011：ログイン
	 * <p>
	 * {@link jp.zein.it.training.was.security.auth.user.UserAuthenticationProvider}で処理するため、このRESTコントローラーは呼び出されない
	 * <p>
	 * 
	 * @param request         {@link LoginRequest}
	 * @param servletResponse {@link HttpServletResponse}
	 * @return {@link LoginResponse}
	 */
	@PostMapping("/user/login")
	@Transactional
	public LoginResponse exec(@RequestBody LoginRequest request, HttpServletResponse servletResponse) {

		// axiosでHTTPレスポンスヘッダーを読み込めるようにする
		// https://note.kiriukun.com/entry/20200303-axios-response-header-could-not-get
		servletResponse.setHeader("Access-Control-Expose-Headers", "X-Auth-Token");

		return modelMapper.map(userSession, LoginResponse.class);
	}
}