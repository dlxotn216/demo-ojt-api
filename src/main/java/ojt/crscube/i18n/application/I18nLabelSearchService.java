package ojt.crscube.i18n.application;

import lombok.RequiredArgsConstructor;
import ojt.crscube.i18n.domain.model.I18n;
import ojt.crscube.i18n.domain.model.Label;
import ojt.crscube.i18n.domain.repository.I18nRepository;
import ojt.crscube.i18n.domain.repository.LabelRepository;
import ojt.crscube.locale.domain.model.ApplicationLocale;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static ojt.crscube.base.utils.Messages.MESSAGE_NOT_FOUND;

/**
 * Created by taesu on 2019-04-20.
 */
@Component @RequiredArgsConstructor
public class I18nLabelSearchService {
    private final I18nRepository i18nRepository;
    private final LabelRepository labelRepository;

    @Transactional(readOnly = true)
    public String searchOrDefault(String i18nId) {
        return searchOrDefault(i18nId, ApplicationLocale.getCurrentLocale());
    }

    @Transactional(readOnly = true)
    public String searchOrDefault(String i18nId, ApplicationLocale locale) {
        List<Label> labels = this.labelRepository.findAllByI18nId(i18nId);
        return labels.stream()
                .filter(label -> label.getApplicationLocale() == locale)
                .findAny()
                .orElseGet(() -> searchDefault(labels))
                .getValue();
    }

    private Label searchDefault(List<Label> labels) {
        return labels.stream()
                .filter(label -> label.getApplicationLocale() == ApplicationLocale.getDefaultLocale())
                .findAny().orElseThrow(() -> new IllegalStateException(MESSAGE_NOT_FOUND));
    }
}

