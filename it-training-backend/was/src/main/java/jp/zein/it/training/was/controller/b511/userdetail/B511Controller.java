package jp.zein.it.training.was.controller.b511.userdetail;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.dao.WasMstUserEntityDao;
import jp.zein.it.training.was.exception.TrainingApplicationException;
import lombok.RequiredArgsConstructor;

/**
 * B511:ユーザー情報取得コントローラー
 *
 */
@RestController
@RequiredArgsConstructor
public class B511Controller {

	private final ModelMapper modelMapper;

	private final WasMstUserEntityDao mstUserEntityDao;

	/**
	 * B511:ユーザー情報取得
	 * 
	 * @param userId ユーザー情報を取得する対象のユーザーID
	 * @return ユーザーIDより取得したユーザー情報（{@link B511Response}）
	 */
	@GetMapping(path = "/user")
	@Transactional
	public B511Response exec(@RequestParam(name = "userId", required = true) String userId) {
		// ユーザーマスタを取得する
		var userDetail = mstUserEntityDao.selectById(userId);

		// ユーザーマスタの存在取得チェック
		if (userDetail == null) {
			// 取引先マスタが存在しない場合はエラー
			throw new TrainingApplicationException("対象のユーザーは削除された可能性があります。管理者にお問い合わせください。");
		}

		// レスポンス情報にマッピングする
		return modelMapper.map(userDetail, B511Response.class);
	}
}