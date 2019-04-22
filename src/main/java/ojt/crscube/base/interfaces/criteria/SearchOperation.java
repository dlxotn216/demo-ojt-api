package ojt.crscube.base.interfaces.criteria;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 * Created by taesu on 2019-04-21.
 */
@AllArgsConstructor
@Getter
public enum SearchOperation {

    EQUAL("="),
    NOT_EQUAL("!="),
    LIKE(":"),
    LESS_THEN("<"),
    LESS_OR_EQUAL_THEN("<="),
    GRATER_THEN(">"),
    GRATER_OR_EQUAL_THEN(">=");

    private String operationString;

    public static SearchOperation fromString(String operationString) {
        return Stream.of(values())
                .filter(searchOperation -> searchOperation.operationString.equalsIgnoreCase(operationString))
                .findAny()
                .orElseThrow(() -> new UnsupportedOperationException(operationString + "은 지원하지 않는 연산자입니다"));
    }

    public static <A extends Number & Comparable<?>> BooleanExpression getBooleanExpression(NumberPath<A> path,
                                                                                            SearchOperation operation,
                                                                                            A value) {
        switch (operation) {
            case EQUAL:
                return path.eq(value);
            case GRATER_THEN:
                return path.gt(value);
            case GRATER_OR_EQUAL_THEN:
                return path.goe(value);
            case LESS_THEN:
                return path.lt(value);
            case LESS_OR_EQUAL_THEN:
                return path.loe(value);
            default:
                return null;
        }
    }

    public static BooleanExpression getBooleanExpression(StringPath path,
                                                         SearchOperation operation,
                                                         String value) {
        switch (operation) {
            case EQUAL:
                return path.equalsIgnoreCase(value);
            case LIKE:
                return path.containsIgnoreCase(value);
            default:
                return null;
        }
    }

    public static BooleanExpression getBooleanExpression(BooleanPath path,
                                                         Boolean value) {
        return path.eq(value);
    }

    public static BooleanExpression getBooleanExpression(DatePath<LocalDate> path,
                                                         SearchOperation operation,
                                                         LocalDate value) {
        return getBooleanExpressionResult(path, operation, value);
    }

    public static BooleanExpression getBooleanExpression(DateTimePath<LocalDateTime> path,
                                                         SearchOperation operation,
                                                         String value) {
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(value);
        } catch (Exception e){
            localDateTime = LocalDate.parse(value).atTime(0,0);
        }
        return getBooleanExpressionResult(path, operation, localDateTime);
    }

    private static <T extends Comparable> BooleanExpression getBooleanExpressionResult(ComparableExpression<T> path,
                                                              SearchOperation operation,
                                                              T value){
        switch (operation) {
            case EQUAL:
                return path.eq(value);
            case GRATER_THEN:
                return path.gt(value);
            case GRATER_OR_EQUAL_THEN:
                return path.goe(value);
            case LESS_THEN:
                return path.lt(value);
            case LESS_OR_EQUAL_THEN:
                return path.loe(value);
            default:
                return null;
        }
    }
}
