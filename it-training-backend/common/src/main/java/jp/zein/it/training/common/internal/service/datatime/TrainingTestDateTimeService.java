package jp.zein.it.training.common.internal.service.datatime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import jp.zein.it.training.common.service.TrainingDateTimeService;

@Component
@ConditionalOnProperty("training.test.system-date-time")
public class TrainingTestDateTimeService implements TrainingDateTimeService {

	/**
	 * テスト日時
	 * <p>
	 * 日時の形式は{@link LocalDateTime#parse(CharSequence)}を参照
	 * </p>
	 */
	@Value("${training.test.system-date-time:#{null}}")
	private Optional<String> testDateTime;

	@Override
	public LocalDateTime now() {
		return testDateTime.map(d -> LocalDateTime.parse(d)).orElseGet(LocalDateTime::now);
	}

	@Override
	public LocalDate today() {
		return LocalDate.from(now());
	}
}
