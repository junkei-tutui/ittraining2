package jp.zein.it.training.was.controller.b513.userupdate;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.dao.WasMstUserEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import lombok.RequiredArgsConstructor;

/**
 * B513:ユーザー更新コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class B513Controller {

	private final ModelMapper modelMapper;

	private final PasswordEncoder passwordEncoder;

	private final WasMstUserEntityDao mstUserEntityDao;

	/**
	 * B513:ユーザー更新
	 * <p>
	 * パスワード変更フラグが{@code true}の場合のみ受け取ったパスワードを<br>
	 * ハッシュ化して更新する。
	 * <p>
	 * 
	 * @param request 更新するユーザー情報（{@link B513Request}）
	 */
	@PostMapping()
	@Transactional
	public void update(@RequestBody B513Request request) {

		// ユーザーの存在チェックを行う
		var userData = mstUserEntityDao.selectById(request.getUserId());
		// 指定のユーザーが有効でない場合はエラーとする。
		if (Objects.isNull(userData)) {
			// ユーザーマスタが存在しない場合はエラー
			throw new TrainingApplicationException("対象のユーザーは削除された可能性があります。管理者にお問い合わせください。");
		}

		// 入力された情報で取引先情報を更新する。
		modelMapper.map(request, userData);

		// パスワード変更フラグにより、パスワード再設定を実施
		if (request.isChangePassword()) {
			userData.setUserPassword(passwordEncoder.encode(request.getInputPassword()));
		}

		// 取引先テーブルを更新する。
		mstUserEntityDao.update(userData);
	}

}