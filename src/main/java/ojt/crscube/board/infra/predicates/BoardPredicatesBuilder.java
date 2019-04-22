package ojt.crscube.board.infra.predicates;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import lombok.extern.slf4j.Slf4j;
import ojt.crscube.base.interfaces.criteria.SearchCriteria;
import ojt.crscube.base.interfaces.criteria.SearchOption;
import ojt.crscube.member.domain.model.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.querydsl.core.types.dsl.Expressions.asBoolean;
import static ojt.crscube.base.interfaces.criteria.SearchOperation.getBooleanExpression;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * Created by taesu on 2019-04-21.
 */
@Slf4j
public final class BoardPredicatesBuilder {
    private BoardPredicatesBuilder() {
    }

    private static final Map<String, Class> PROPERTY_OWNER_MAP = new HashMap<>();
    private static final Map<String, Class> PROPERTY_TYPE_MAP = new HashMap<>();

    static {
        PROPERTY_OWNER_MAP.put("updatedBy", Member.class);
        PROPERTY_TYPE_MAP.put("entityBase.updateDateTime", LocalDateTime.class);
        PROPERTY_TYPE_MAP.put("entityBase.deleted", Boolean.class);
    }

    public static BooleanExpression buildPredicate(SearchCriteria searchCriteria,
                                                   String memberAlias,
                                                   String boardAlias) {
        if (isEmpty(searchCriteria.getOptions())) {
            return null;
        }

        return searchCriteria.getOptions().stream()
                             .map(criteria -> withExpression(criteria, memberAlias, boardAlias))
                             .filter(Objects::nonNull)
                             .reduce(searchCriteria.isAndCondition()
                                             ? asBoolean(true).isTrue()
                                             : asBoolean(false).isTrue(),
                                     searchCriteria.isAndCondition()
                                             ? BooleanExpression::and
                                             : BooleanExpression::or);
    }

    private static BooleanExpression withExpression(SearchOption searchCriteria, String memberAlias,
                                                    String boardAlias) {
        PathBuilder<?> entityPath;
        if (PROPERTY_OWNER_MAP.getOrDefault(searchCriteria.getKey(), Member.class) == Member.class) {
            entityPath = new PathBuilder<>(Member.class, boardAlias);
        } else {
            entityPath = new PathBuilder<>(Member.class, memberAlias);
        }

        final Class propertyType = PROPERTY_TYPE_MAP.getOrDefault(searchCriteria.getKey(), String.class);
        if (propertyType == Long.class) {
            return getBooleanExpression(entityPath.getNumber(searchCriteria.getKey(), Long.class),
                                        searchCriteria.getOperation(),
                                        Long.valueOf(searchCriteria.getValue()));
        } else if (propertyType == Double.class) {
            return getBooleanExpression(entityPath.getNumber(searchCriteria.getKey(), Double.class),
                                        searchCriteria.getOperation(),
                                        Double.valueOf(searchCriteria.getValue()));
        } else if (propertyType == Integer.class) {
            return getBooleanExpression(entityPath.getNumber(searchCriteria.getKey(), Integer.class),
                                        searchCriteria.getOperation(),
                                        Integer.valueOf(searchCriteria.getValue()));
        } else if (propertyType == LocalDate.class) {
            return getBooleanExpression(entityPath.getDate(searchCriteria.getKey(), LocalDate.class),
                                        searchCriteria.getOperation(),
                                        LocalDate.parse(searchCriteria.getValue()));
        } else if (propertyType == LocalDateTime.class) {
            return getBooleanExpression(entityPath.getDateTime(searchCriteria.getKey(), LocalDateTime.class),
                                        searchCriteria.getOperation(),
                                        searchCriteria.getValue());
        } else if (propertyType == String.class) {
            return getBooleanExpression(entityPath.getString(searchCriteria.getKey()),
                                        searchCriteria.getOperation(),
                                        searchCriteria.getValue());
        } else if (propertyType == Boolean.class) {
            return getBooleanExpression(entityPath.getBoolean(searchCriteria.getKey()),
                                        Boolean.valueOf(searchCriteria.getValue()));
        }

        return null;
    }

    public static OrderSpecifier[] buildOrder(Pageable pageable, String memberAlias, String boardAlias) {
        return pageable.getSort().stream()
                       .map(order -> withOrder(memberAlias, boardAlias, order))
                       .toArray(OrderSpecifier[]::new);
    }

    private static OrderSpecifier<? extends Serializable> withOrder(String memberAlias, String boardAlias,
                                                                    Sort.Order order) {
        PathBuilder<?> entityPath;
        String property = order.getProperty();
        if (property.equalsIgnoreCase("updatedBy")) {
            property = "entityBase.updatedBy";
        } else if (property.equalsIgnoreCase("updateDateTime")) {
            property = "entityBase.updateDateTime";
        }

        if (PROPERTY_OWNER_MAP.getOrDefault(property, Member.class) == Member.class) {
            entityPath = new PathBuilder<>(Member.class, boardAlias);
        } else {
            entityPath = new PathBuilder<>(Member.class, memberAlias);
        }

        final Class propertyType = PROPERTY_TYPE_MAP.getOrDefault(property, String.class);
        if (propertyType == Long.class) {
            return new OrderSpecifier<>(Order.valueOf(order.getDirection().name()),
                                        entityPath.getNumber(property, Long.class));
        } else if (propertyType == Double.class) {
            return new OrderSpecifier<>(Order.valueOf(order.getDirection().name()),
                                        entityPath.getNumber(property, Double.class));
        } else if (propertyType == Integer.class) {
            return new OrderSpecifier<>(Order.valueOf(order.getDirection().name()),
                                        entityPath.getNumber(property, Integer.class));
        } else if (propertyType == LocalDate.class) {
            return new OrderSpecifier<>(Order.valueOf(order.getDirection().name()),
                                        entityPath.getDate(property, LocalDate.class));
        } else if (propertyType == LocalDateTime.class) {
            return new OrderSpecifier<>(Order.valueOf(order.getDirection().name()),
                                        entityPath.getDateTime(property, LocalDateTime.class));
        } else if (propertyType == String.class) {
            return new OrderSpecifier<>(Order.valueOf(order.getDirection().name()),
                                        entityPath.getString(property));
        }
        return null;
    }
}
