package jp.zein.it.training.was.security.auth.user;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import jp.zein.it.training.was.dao.WasMstUserEntityDao;
import jp.zein.it.training.was.exception.TrainingAuthenticationException;
import jp.zein.it.training.was.session.user.UserSession;
import lombok.RequiredArgsConstructor;

/**
 * ユーザー画面用認証プロバイダ
 *
 */
@Configuration
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {

	private final UserSession userSession;

	private final WasMstUserEntityDao mstUserEntityDao;

	private final ModelMapper modelMapper;

	private final PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {

		String userId = (String) auth.getPrincipal();
		String password = (String) auth.getCredentials();

		// パスワードをハッシュ化する。不可逆の暗号化方式：Bcrypt（ver.2b）を使用する。

		// 3) ログインチェック処理
		var loginUser = mstUserEntityDao.selectById(userId);
		if (Objects.isNull(loginUser)) {
			throw new TrainingAuthenticationException("auth error", "IDもしくはパスワードが異なります。");
		}
		if (!passwordEncoder.matches(password, loginUser.getUserPassword())) {
			throw new TrainingAuthenticationException("auth error", "IDもしくはパスワードが異なります。");
		}

		// セッション情報を保持する。
		modelMapper.map(loginUser, userSession);

		// trainingの為、権限はUSERで固定
		var authorities = AuthorityUtils.createAuthorityList("USER");

		return new UsernamePasswordAuthenticationToken(new User(userId, password, authorities), userId, authorities);

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UserAuthenticationToken.class.isAssignableFrom(authentication);
	}
}