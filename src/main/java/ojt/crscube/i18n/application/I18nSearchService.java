package ojt.crscube.i18n.application;

import lombok.RequiredArgsConstructor;
import ojt.crscube.i18n.domain.model.I18n;
import ojt.crscube.i18n.domain.repository.I18nRepository;
import ojt.crscube.locale.domain.model.ApplicationLocale;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by taesu on 2019-04-20.
 */
@Component @RequiredArgsConstructor
public class I18nSearchService {
    private final I18nRepository i18nRepository;
    private final I18nLabelSearchService i18nLabelSearchService;

    public Map<String, String> searchI18nsByEntry(List<String> i18nIds) {
        if (CollectionUtils.isEmpty(i18nIds)) {
            return searchAllByEntry();
        }

        return this.i18nRepository.findAllByI18nIdInAndLocale(i18nIds, ApplicationLocale.getCurrentLocale())
                .stream()
                .collect(Collectors.toMap(I18n::getId, this::getOrDefault));
    }

    public Map<String, String> searchAllByEntry() {
        return this.i18nRepository.findAllByLocale(ApplicationLocale.getCurrentLocale())
                .stream()
                .collect(Collectors.toMap(I18n::getId, this::getOrDefault));
    }

    private String getOrDefault(I18n i18n) {
        return CollectionUtils.isEmpty(i18n.getLabels())
                ? this.i18nLabelSearchService.searchOrDefault(i18n.getId())
                : i18n.getLabels().get(0).getValue();
    }
}
