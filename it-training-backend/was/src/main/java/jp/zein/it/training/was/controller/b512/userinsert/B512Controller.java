package jp.zein.it.training.was.controller.b512.userinsert;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.common.generate.entity.MstUserEntity;
import jp.zein.it.training.was.dao.WasMstUserEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import lombok.RequiredArgsConstructor;

/**
 * B512:ユーザー登録コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class B512Controller {

	private final ModelMapper modelMapper;

	private final PasswordEncoder passwordEncoder;

	private final WasMstUserEntityDao mstUserEntityDao;

	/**
	 * B512:ユーザー登録
	 * 
	 * @param request 新規登録するユーザー情報（{@link B512Request}）
	 */
	@PutMapping()
	@Transactional
	public void insert(@RequestBody B512Request request) {

		// ユーザーの存在チェックを行う
		var userData = mstUserEntityDao.selectByIdIncLD(request.getUserId());
		// 指定のユーザーIDで有効なレコードが存在する場合はエラーとする。
		if (!Objects.isNull(userData) && !userData.getIsDelete()) {
			// ユーザーマスタが存在かつ、削除フラグがOFFの場合はエラー
			throw new TrainingApplicationException("対象のユーザーは既に存在しています。");
		}

		// パスワードをハッシュ化する。
		var encPassword = passwordEncoder.encode(request.getInputPassword());

		// レコードの存在有無で、処理を変更する。
		if (Objects.isNull(userData)) {
			// 新規レコードを作成する。
			var entryRecode = new MstUserEntity();
			// 入力された情報を登録用エンティティにマッピングする。
			modelMapper.map(request, entryRecode);
			// 固定情報の情報のみ再設定
			// パスワードを再設定する
			entryRecode.setUserPassword(encPassword);
			// 削除フラグ（OFF）
			entryRecode.setIsDelete(false);

			// 取引先マスタに登録する。
			mstUserEntityDao.insert(entryRecode);
		} else {
			// 入力された情報で取引先情報を更新する。
			modelMapper.map(request, userData);
			// パスワードを再設定する
			userData.setUserPassword(encPassword);
			// 削除フラグは有効に変更する。
			userData.setIsDelete(false);

			// 取引先マスタを更新する。
			mstUserEntityDao.update(userData);
		}
	}

}