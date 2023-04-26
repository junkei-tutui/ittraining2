package jp.zein.it.training.was.util.csv;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * テキストファイル・ユーティリティ
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TextFileUtils {
	/**
	 * BOM
	 */
	private static final byte BOM[] = new byte[] { (byte) 0xef, (byte) 0xbb, (byte) 0xbf };

	public static final String BOM_STRING = "\uFEFF";

	/**
	 * BOMを除去した文字列を取得
	 *
	 * @param s
	 * @return
	 */
	public static String removeUTF8BOM(String s) {
		if (s.startsWith(BOM_STRING)) {
			// ファイルの先頭より後ろの文字列を読み込む
			s = s.substring(1);
		}
		return s;
	}

	/**
	 * 文字列をBOMを付与したバイナリに変換
	 *
	 * @param text 文字列
	 * @return バイナリ
	 * @throws IOException
	 */
	public static byte[] toBinaryWithBom(String text) throws IOException {
		var utf8Text = text.getBytes(StandardCharsets.UTF_8);

		var out = new ByteArrayOutputStream(utf8Text.length + BOM.length);
		out.write(BOM);
		out.write(utf8Text);

		return out.toByteArray();
	}
}
