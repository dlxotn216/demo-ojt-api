package ojt.crscube.i18n.domain.repository;

import ojt.crscube.i18n.domain.model.I18n;
import ojt.crscube.locale.domain.model.ApplicationLocale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by taesu at : 2019-04-17
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
public interface I18nRepository extends JpaRepository<I18n, Long> {
    Optional<I18n> findById(String id);

    @Query("select i from I18n i join fetch i.labels la where la.applicationLocale = :locale")
    List<I18n> findAllByLocale(@Param("locale") ApplicationLocale locale);

    @Query("select i from I18n i join fetch i.labels la where i.id in :i18nIds and la.applicationLocale = :locale")
    List<I18n> findAllByI18nIdInAndLocale(@Param("i18nIds") List<String> i18nIds,
                                          @Param("locale") ApplicationLocale locale);
}
