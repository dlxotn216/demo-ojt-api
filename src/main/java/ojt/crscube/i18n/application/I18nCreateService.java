package ojt.crscube.i18n.application;

import lombok.RequiredArgsConstructor;
import ojt.crscube.i18n.domain.model.I18n;
import ojt.crscube.i18n.domain.model.Label;
import ojt.crscube.i18n.domain.repository.I18nRepository;
import ojt.crscube.i18n.interfaces.dto.I18nDto.I18nCreateRequest;
import ojt.crscube.i18n.interfaces.dto.I18nDto.I18nCreateResponse;
import ojt.crscube.locale.domain.model.ApplicationLocale;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

import static ojt.crscube.i18n.interfaces.dto.I18nDto.fromI18n;
import static ojt.crscube.locale.domain.model.ApplicationLocale.valueOf;

/**
 * Created by taesu at : 2019-04-17
 *
 * 다국어 생성 서비스
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class I18nCreateService {
    private final I18nRepository i18nRepository;

    public I18nCreateResponse createI18n(I18nCreateRequest request) {
        Map<ApplicationLocale, String> localeLabelMap = request.getConvertedLocaleLabelMap();
        if (CollectionUtils.isEmpty(localeLabelMap)) {
            throw new IllegalArgumentException("");
        }

        final I18n i18n = I18n.builder().id(request.getI18nId()).build();
        localeLabelMap.forEach((locale, value) -> i18n.addLabel(Label.from(i18n, valueOf(locale.toString()), value)));

        return fromI18n(this.i18nRepository.save(i18n));
    }
}
