package jp.zein.it.training.was.controller.b502.userdelete;

import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.dao.WasMstUserEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import lombok.RequiredArgsConstructor;

/**
 * B502:ユーザー削除コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class B502Controller {

	private final WasMstUserEntityDao mstUserEntityDao;

	/**
	 * B502:ユーザー削除
	 * 
	 * @param userId 削除対象のユーザーID
	 */
	@DeleteMapping()
	@Transactional
	public void delete(@RequestParam(name = "userId", required = true) String userId) {

		// 取引先の存在チェックを行う
		var userData = mstUserEntityDao.selectById(userId);
		// 指定のユーザーが有効でない場合はエラーとする。
		if (Objects.isNull(userData)) {
			// ユーザーマスタが存在しない場合はエラー
			throw new TrainingApplicationException("対象のユーザーは削除された可能性があります。管理者にお問い合わせください。");
		}

		// 取引先レコードの削除フラグをONにする。
		userData.setIsDelete(true);

		// 取引先を論理削除する。
		mstUserEntityDao.update(userData);
	}

}