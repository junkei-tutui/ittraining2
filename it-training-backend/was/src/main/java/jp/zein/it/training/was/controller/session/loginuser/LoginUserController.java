package jp.zein.it.training.was.controller.session.loginuser;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.session.user.UserSession;
import lombok.RequiredArgsConstructor;

/**
 * B012:セッションユーザー情報取得コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
public class LoginUserController {

	private final UserSession userSession;

	private final ModelMapper modelMapper;

	/**
	 * B012:セッションユーザー情報取得
	 * <p>
	 * セッションよりWASで管理しているセッションユーザー情報を返却する
	 * <p>
	 * 
	 * @return WASで管理するセッションユーザー情報（{@link LoginUserResponse}）を返却
	 */
	@GetMapping(path = "/user/session")
	@Transactional
	public LoginUserResponse exec() {
		// 単純にセッション情報を返却するだけとする
		return modelMapper.map(userSession, LoginUserResponse.class);
	}
}