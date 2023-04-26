package jp.zein.it.training.common.internal.service.datatime;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import jp.zein.it.training.common.service.TrainingDateTimeService;

@Component
@ConditionalOnMissingBean(TrainingTestDateTimeService.class)
public class TrainingDateTimeServiceImpl implements TrainingDateTimeService {

	@Override
	public LocalDateTime now() {
		return LocalDateTime.now();
	}

	@Override
	public LocalDate today() {
		return LocalDate.now();
	}

}
