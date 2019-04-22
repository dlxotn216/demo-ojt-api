package ojt.crscube.i18n.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ojt.crscube.i18n.domain.model.I18n;
import ojt.crscube.i18n.domain.model.Label;
import ojt.crscube.locale.domain.model.ApplicationLocale;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static ojt.crscube.base.utils.Messages.REQUIRED_PARAMETER;
import static ojt.crscube.base.utils.VariableUtils.EMPTY_STRING;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * Created by taesu at : 2019-04-17
 *
 * I18n Interfaces layer Dto
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
public final class I18nDto {
    private I18nDto() {
    }

    @NoArgsConstructor @AllArgsConstructor
    public static class I18nCreateRequest {
        private String i18nId;
        private Map<Locale, String> localeLabelMap;

        public void setI18nId(String i18nId) {
            this.i18nId = i18nId;
        }

        public String getI18nId() {
            return i18nId;
        }

        public void setLocaleLabelMap(Map<Locale, String> localeLabelMap) {
            this.localeLabelMap = localeLabelMap;
        }

        public Map<ApplicationLocale, String> getConvertedLocaleLabelMap() {
            Map<ApplicationLocale, String> convertedLocaleLabelMap
                    = localeLabelMap.keySet().stream().map(ApplicationLocale.CONVERTER)
                                    .collect(toMap(o -> o, o -> this.localeLabelMap.computeIfAbsent(o.getLocale(),
                                                                                                    locale -> EMPTY_STRING)));

            //라벨 값이 존재하지 않는 다국어 locale
            List<Map.Entry<ApplicationLocale, String>> hasNoLabelValueTargets
                    = convertedLocaleLabelMap.entrySet().stream()
                                             .filter(entry -> isEmpty(entry.getValue()))
                                             .collect(toList());

            if (CollectionUtils.isEmpty(hasNoLabelValueTargets)) {
                return convertedLocaleLabelMap;
            }

            String defaultLabel = convertedLocaleLabelMap.get(ApplicationLocale.getDefaultLocale());
            if (isEmpty(defaultLabel)) {
                throw new IllegalArgumentException(REQUIRED_PARAMETER);
            }

            hasNoLabelValueTargets.forEach(entry -> entry.setValue(defaultLabel));
            return convertedLocaleLabelMap;
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class I18nCreateResponse {
        private String i18nId;
        private Map<Locale, String> localeLabelMap;

        public static I18nCreateResponse fromI18n(I18n i18n) {
            I18nCreateResponse res = new I18nCreateResponse();
            res.setI18nId(i18n.getId());
            res.setLocaleLabelMap(i18n.getLabels().stream()
                    .collect(toMap(o -> o.getApplicationLocale().getLocale(), Label::getValue)));
            return res;
        }
    }
}
