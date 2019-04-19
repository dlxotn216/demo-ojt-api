package ojt.crscube.i18n.domain.repository;

import ojt.crscube.i18n.domain.model.I18n;
import ojt.crscube.i18n.domain.model.Label;
import ojt.crscube.locale.domain.model.ApplicationLocale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by taesu at : 2019-04-17
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
public interface LabelRepository extends JpaRepository<Label, Long> {
    List<Label> findAllByI18n(I18n i18n);
}
