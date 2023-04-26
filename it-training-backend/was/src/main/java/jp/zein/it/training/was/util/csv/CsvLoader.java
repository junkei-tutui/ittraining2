package jp.zein.it.training.was.util.csv;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.Size;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser.Feature;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import jp.zein.it.training.was.exception.TrainingApplicationException;
import jp.zein.it.training.was.exception.TrainingSystemException;
import jp.zein.it.training.was.util.csv.annotation.CsvUnique;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * CSV読み込みクラス
 *
 * <p>
 * 当クラスの責務は、CSVレイアウト定義書の「2.CSVファイル定義」に沿ったCSV読み込みおよびバリデーションである。
 * </p>
 *
 * @param <T> CSV格納Bean
 */
@Component
@RequestScope
@RequiredArgsConstructor
public class CsvLoader<T extends CsvBean> {

	/**
	 * データ行単位の管理
	 *
	 */
	public static class DataLine<T> {

		private static final String CSV_DELIMITER = ",";

		/**
		 * CSVテキスト
		 */
		@Getter
		private final String text;

		/**
		 * データ行ごとのCSV格納Bean
		 *
		 * {@link #parse()}を呼び出すまで{@code null}
		 */
		@Getter
		private T csvBean;

		/**
		 * データ行ごとの検証エラー・メッセージ
		 */
		private List<String> validationErrorMessageList = new ArrayList<>();;

		/**
		 * {@link CsvMapper}
		 */
		private final CsvMapper mapper;

		/**
		 * {@link CsvSchema}
		 */
		private final CsvSchema schema;

		/**
		 * CSV格納Beanクラス
		 */
		private final Class<T> csvBeanClass;

		/**
		 * コンストラクタ
		 *
		 * @param text CVSテキスト
		 */
		private DataLine(CsvMapper mapper, CsvSchema schema, Class<T> csvBeanClass, String text) {
			this.mapper = mapper;
			this.schema = schema;
			this.csvBeanClass = csvBeanClass;
			this.text = text;
		}

		/**
		 * CSV列ごとに分解したデータを取得
		 *
		 * @return CSV列ごとに分解したデータ
		 * @throws JsonMappingException
		 * @throws JsonProcessingException
		 */
		public String[] getSplitLine() throws JsonMappingException, JsonProcessingException {
			return mapper.readerFor(String[].class).readValue(text);
		}

		/**
		 * CSVデータをパース
		 *
		 * @throws IOException
		 */
		@SuppressWarnings("unchecked")
		private void parse() throws IOException {
			var it = mapper.readerFor(csvBeanClass).with(schema).readValues(text);
			try {
				csvBean = (T) it.next();
			} catch (NoSuchElementException ex) { // 空行
				csvBean = null;
			}
		}

		/**
		 * 検証エラーメッセージを追加
		 *
		 * @param validationErrorMessage 検証エラーメッセージ
		 */
		public void appendValidationErrorMessage(String validationErrorMessage) {
			validationErrorMessageList.add(validationErrorMessage);
		}

		/**
		 * 検証エラーメッセージ付きのデータ行を取得
		 *
		 * @return 検証エラーメッセージ付きのデータ行
		 */
		private String getTextWithValidationErrorMessage() {
			if (validationErrorMessageList.isEmpty()) {
				return text;
			} else {
				return text + CSV_DELIMITER //
						+ validationErrorMessageList.stream().map(StringEscapeUtils::escapeCsv)
								.collect(Collectors.joining(CSV_DELIMITER));
			}
		}

		/**
		 * 検証エラー有無を取得
		 *
		 * @return 検証エラー有無
		 */
		public boolean hasError() {
			return !validationErrorMessageList.isEmpty();
		}
	}

	/**
	 * タイトル行
	 * <p>
	 * BOMは含まない
	 * </p>
	 */
	private String headerLine;

	/**
	 * データ行
	 */
	private List<DataLine<T>> dataLineList;

//	/**
//	 * 処理区分=その他を含まないデータ行
//	 */
//	private List<DataLine<T>> excludeNoneDataLineList;

	/**
	 * {@link CsvMapper}
	 */
	private CsvMapper mapper;

	/**
	 * {@link CsvSchema}
	 */
	private CsvSchema schema;

	/**
	 * CSV格納Beanクラス
	 */
	private Class<T> csvBeanClass;

	/**
	 * 重複チェック対象フィールド一覧
	 */
	private List<String> validateDuplicateFieldNameList;

	/**
	 * フィールド名とCSV項目名のマップ
	 */
	private Map<String, String> columnNameMap = new HashMap<>();

	/**
	 * {@link Validator}
	 */
	private final Validator validator;

	/**
	 * CSVデータの読み込み
	 *
	 * @param csvBeanClass CSV格納Beanクラス
	 * @param csvStream    CSVテキストを読み込むStream
	 * @throws IOException CSVテキストの読み込み失敗
	 */
	public void load(Class<T> csvBeanClass, InputStream csvStream) throws IOException {

		this.csvBeanClass = csvBeanClass;

		mapper = (new CsvMapper())
				// 余分な列の読み込みを無視する
				.configure(Feature.IGNORE_TRAILING_UNMAPPABLE, true)
				// 空文字列をnullとして読み込む
				// 実装メモ：BeanValidationのPatternやDigitなどは空文字列で検証エラーになる。空文字列を検証エラーにしないため、nullにしている。
				.configure(Feature.EMPTY_STRING_AS_NULL, true);
		schema = mapper.schemaFor(csvBeanClass);

		// CSVテキストの読み込み
		var lines = IOUtils.readLines(csvStream, StandardCharsets.UTF_8);
		if (lines.isEmpty()) { // 空ファイルの場合
			throw new TrainingSystemException("import line error", "項目数が不正です。");
		}

		headerLine = TextFileUtils.removeUTF8BOM(lines.get(0));
		lines.remove(0);
		dataLineList = lines.stream().map(v -> new DataLine<T>(mapper, schema, csvBeanClass, v))
				.collect(Collectors.toUnmodifiableList());

		// 重複チェック対象フィールド名一覧を抽出
		validateDuplicateFieldNameList = Arrays.stream(csvBeanClass.getDeclaredFields())
				.filter(v -> !Objects.isNull(v.getAnnotation(CsvUnique.class)))//
				.map(Field::getName) //
				.collect(Collectors.toList());

		// フィールド名とCSV項目名を抽出
		columnNameMap = Arrays.stream(csvBeanClass.getDeclaredFields()) //
				.filter(v -> v.getAnnotation(JsonProperty.class) != null)//
				.collect(Collectors.toMap(v -> v.getName(), v -> v.getAnnotation(JsonProperty.class).value()));
	}

	/**
	 * ヘッダー行テキストを取得
	 *
	 * @return ヘッダー行テキスト
	 */
	public String getHeaderLine() {
		return headerLine;
	}

	/**
	 * CSV列ごとに分解したヘッダー行を取得
	 *
	 * @return CSV列ごとに分解したヘッダー行
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public String[] getSplitHeaderLine() throws JsonMappingException, JsonProcessingException {
		return mapper.readerFor(String[].class).readValue(headerLine);
	}

	/**
	 * CSVデータの行一覧を取得
	 *
	 * @return CSVデータの行一覧
	 */
	public List<DataLine<T>> getDataLineList() {
		return dataLineList;
	}

	/**
	 * 検証エラー有無を取得
	 *
	 * @return 検証エラーありなら{@code true}
	 */
	public boolean hasError() {
		return dataLineList.stream().filter(DataLine::hasError).findAny().isPresent();
	}

	/**
	 * CSVテキストをパース
	 *
	 * @throws IOException
	 */
	public void parse() throws IOException {
		for (var dataLine : dataLineList) {
			dataLine.parse();
		}
	}

	/**
	 * 検証エラー付きのCSVテキストを取得
	 *
	 * <p>
	 * BOMは含まない
	 * </p>
	 * <p>
	 * 改行はwindows形式
	 * </p>
	 *
	 * @return 検証エラー付きのCSVテキスト
	 */
	public String getTextWithValidationErrorMessage() {
		var list = new ArrayList<String>();
		list.add(headerLine);
		list.addAll(
				dataLineList.stream().map(DataLine::getTextWithValidationErrorMessage).collect(Collectors.toList()));
		return list.stream().collect(Collectors.joining(IOUtils.LINE_SEPARATOR_WINDOWS));
	}

	/**
	 * CSVデータの検証
	 *
	 * <p>
	 * 「2.CSVファイル定義」シートの検証を実施
	 * </p>
	 *
	 * @return 検証エラーなしなら{@code true}。検証エラーありなら{@code false}
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public boolean validate() throws IOException, IllegalArgumentException, IllegalAccessException {
		validateHeader();
		parse();
		validateData();

		return hasError();
	}

	// ------------------------------------------------------------------------
	// ヘッダー検証
	// ------------------------------------------------------------------------
	/**
	 * ヘッダーの検証
	 *
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	private void validateHeader() throws JsonMappingException, JsonProcessingException {
		var headerColumns = getSplitHeaderLine();

		// 1) 項目数チェック（ヘッダ）
		if (schema.size() != headerColumns.length) {
			throw new TrainingApplicationException("import line error", "項目数が不正です。");
		}

		// 2) 項目名チェック
		for (int i = 0; i < schema.size(); i++) {
			if (!StringUtils.equals(schema.columnName(i), headerColumns[i].trim())) {
				throw new TrainingApplicationException("import line error", "レイアウトが異なります。");
			}
		}
	}

	// ------------------------------------------------------------------------
	// データ行検証
	// ------------------------------------------------------------------------
	/**
	 * データ行の検証
	 *
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private void validateData() throws IllegalArgumentException, IllegalAccessException {

		dataLineList.stream()//
				.forEach(dataLine -> {

					try {
						// 3) 項目数チェック（レコードチェック）
						if (!validateColumnCount(dataLine)) { // 尚、当チェックでエラーとなった場合は以降のチェックは不要とする。
							return;
						}

						// 4) 項目必須チェック（単項目チェック）
						// 5) 文字桁数チェック（単項目チェック）
						// 6) 項目フォーマットチェック（単項目チェック）
						validateFormat(dataLine);

					} catch (Exception ex) {
						throw new TrainingSystemException("system error", "システムエラーが発生しました。", ex);
					}

				});

		// 8) レコード重複チェック（相関チェック）
		validateDuplicate();
	}

	/**
	 * 3) 項目数チェック（レコードチェック）
	 *
	 * <p>
	 * レコード単位で項目数（列数）がレイアウト定義と異なる場合はエラーとする。
	 * </p>
	 *
	 * @param dataLine {@link DataLine}
	 * @return 検証エラーなしなら{@code true}
	 *
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	private boolean validateColumnCount(DataLine<T> dataLine) throws JsonMappingException, JsonProcessingException {
		if (dataLine.getSplitLine().length != schema.size()) {
			dataLine.appendValidationErrorMessage("項目数が不正です。");
			return false;
		}

		return true;
	}

	/**
	 * 4) 項目必須チェック（単項目チェック）、 5) 文字桁数チェック（単項目チェック）、 6) 項目フォーマットチェック（単項目チェック）
	 *
	 * @param dataLine {@link DataLine}
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	private void validateFormat(DataLine<T> dataLine) throws NoSuchFieldException, SecurityException {
		var errorResult = validator.validate(dataLine.getCsvBean());

		for (var error : errorResult) {
			dataLine.appendValidationErrorMessage(getValidationErrorMessageByValidateError(error));
		}
	}

	/**
	 * 検証エラーから検証エラーメッセージを取得
	 *
	 * @param violation 検証エラー
	 * @return 検証エラーメッセージ
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	private String getValidationErrorMessageByValidateError(ConstraintViolation<?> violation)
			throws NoSuchFieldException, SecurityException {

		// 検証エラーに該当する検証エラーメッセージIDと追加パラメータを取得
		var messageIdAndParameters = validationErrorMessageIdMap.get(violation.getMessageTemplate());
		if (Objects.isNull(messageIdAndParameters)) {
			messageIdAndParameters = DEFAULT_MESSAGE_ID_AND_PARAMETERS;
		}

		// 検証エラーのプロパティに該当するCSV項目名を取得
		var columnName = csvBeanClass.getDeclaredField(violation.getPropertyPath().toString())
				.getAnnotation(JsonProperty.class).value();

		// 検証エラーメッセージを取得
		return MessageFormat.format(messageIdAndParameters.getMessageId(), ArrayUtils.addAll(
				new Object[] { columnName },
				messageIdAndParameters.getParameterFunc().apply(violation.getConstraintDescriptor().getAnnotation())));
	}

	@Getter
	@RequiredArgsConstructor
	private static class MessageFormatAndParameters {
		private static final Object[] EMPTY_OBJECTS = new Object[] {};
		public static final Function<Annotation, Object[]> NO_PARAMETERS = v -> EMPTY_OBJECTS;

		/**
		 * 検証エラーメッセージID
		 */
		private final String messageId;

		/**
		 * 検証エラーメッセージの追加パラメータ取得
		 */
		private final Function<Annotation, Object[]> parameterFunc;
	}

	/**
	 * デフォルトのMessageIdAndParameters
	 */
	private static final MessageFormatAndParameters DEFAULT_MESSAGE_ID_AND_PARAMETERS = new MessageFormatAndParameters(
			"MSG00005", MessageFormatAndParameters.NO_PARAMETERS);

	/**
	 * BeanValidationエラーとwas-darwin検証エラーのマッピング
	 *
	 * <p>
	 * BeanValidationのアノテーションクラスでマッピングしたかったが、ConstraintViolationで取得できるアノテーションインスタンスがProxy化されているため、実クラスが取得できなかった。
	 * </p>
	 */
	private final Map<String, MessageFormatAndParameters> validationErrorMessageIdMap = Map.of(
			// 入力必須
			"{javax.validation.constraints.NotEmpty.message}",
			new MessageFormatAndParameters("{0}は必須です。", MessageFormatAndParameters.NO_PARAMETERS),
			"{javax.validation.constraints.NotBlank.message}",
			new MessageFormatAndParameters("{0}は必須です。", MessageFormatAndParameters.NO_PARAMETERS),
			"{javax.validation.constraints.NotNull.message}",
			new MessageFormatAndParameters("{0}は必須です。", MessageFormatAndParameters.NO_PARAMETERS),

			// 文字数
			"{javax.validation.constraints.Size.message}",
			new MessageFormatAndParameters("{0}は{1}文字以上、{2}文字以内で設定してください。",
					v -> new Object[] { ((Size) v).min(), ((Size) v).max() }),

			// 形式
			"{javax.validation.constraints.Pattern.message}", DEFAULT_MESSAGE_ID_AND_PARAMETERS);

	/**
	 * 8) レコード重複チェック（相関チェック）
	 *
	 * @param dataLine {@link DataLine}
	 * @throws IllegalAccessException
	 */
	private void validateDuplicate() throws IllegalAccessException {

		for (var targetFieldName : validateDuplicateFieldNameList) {

			var values = new HashSet<Object>();

			for (var dataLine : dataLineList) {
				// フィールドの値を取得
				var value = FieldUtils.readDeclaredField(dataLine.getCsvBean(), targetFieldName, true);
				if (Objects.isNull(value) || (value instanceof String && StringUtils.isBlank((String) value))) {
					continue;
				}

				// 重複チェック
				if (values.contains(value)) {
					dataLine.appendValidationErrorMessage(
							MessageFormat.format("{0}が重複しています。", getColumnNameFromField(targetFieldName)));
				} else {
					values.add(value);
				}

			}
		}
	}

	/**
	 * {@link Field}に該当するCSV項目名を取得
	 *
	 * @param fieldName フィールド名
	 * @return CSV項目名
	 */
	private String getColumnNameFromField(String fieldName) {
		return columnNameMap.get(fieldName);
	}
}
