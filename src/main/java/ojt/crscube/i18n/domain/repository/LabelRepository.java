package ojt.crscube.i18n.domain.repository;

import ojt.crscube.i18n.domain.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by taesu at : 2019-04-17
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
public interface LabelRepository extends JpaRepository<Label, Long> {
    List<Label> findAllByI18nId(String i18nId);
}
