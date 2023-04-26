package jp.zein.it.training.was.controller.b501.userlist;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.zein.it.training.was.dao.WasMstUserEntityDao;
import lombok.RequiredArgsConstructor;

/**
 * B501:ユーザー検索一覧取得
 *
 */
@RestController
@RequiredArgsConstructor
public class B501Controller {

	private final ModelMapper modelMapper;

	private final WasMstUserEntityDao mstUserEntityDao;

	/**
	 * B501:ユーザー検索一覧取得
	 * 
	 * @param likeUserName 検索条件となるユーザー名（あいまい検索）
	 * @return 検索条件に該当するユーザーマスタ（{@link B501ResponseRow}））のリスト
	 */
	@GetMapping("/user/list")
	@Transactional
	public List<B501ResponseRow> getList(@RequestParam(name = "likeUserName", required = false) String likeUserName) {

		// 取引先より仕入先一覧を取得する
		var list = mstUserEntityDao.searchUserListByConditions(likeUserName).stream().map(v -> {
			var result = new B501ResponseRow();
			// ユーザーIDを設定
			modelMapper.map(v, result);
			return result;
		}).collect(Collectors.toList());

		// レスポンス情報にマッピングする
		return list;
	}

}