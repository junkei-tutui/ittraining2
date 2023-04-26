package jp.zein.it.training.was.util.csv.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * チェック・アノテーション
 *
 * <p>
 * KEY設定ありのカラムに付与
 * </p>
 *
 */
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvUnique {

}
