package ojt.crscube.base.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by taesu at : 2019-04-12
 *
 * Application에서 사용해야하는 Base Repository로
 * 모든 Delete API에 대해 예외를 던지도록 default 메소드가 구현되어있다
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@NoRepositoryBean
public interface BaseRepository<T, K> extends JpaRepository<T, K> {
    @Override
    default void deleteInBatch(Iterable<T> iterable) {
        throwUnsupportedOperationException();
    }

    @Override
    default void deleteAllInBatch() {
        throwUnsupportedOperationException();
    }

    @Override
    default void deleteById(K id) {
        throwUnsupportedOperationException();
    }

    @Override
    default void delete(T t) {
        throwUnsupportedOperationException();
    }

    @Override
    default void deleteAll(Iterable<? extends T> iterable) {
        throwUnsupportedOperationException();
    }

    @Override
    default void deleteAll() {
        throwUnsupportedOperationException();
    }

    default void throwUnsupportedOperationException() {
        throw new UnsupportedOperationException("Delete method not allowed");
    }
}
