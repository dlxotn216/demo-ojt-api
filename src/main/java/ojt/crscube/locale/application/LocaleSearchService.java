package ojt.crscube.locale.application;

import ojt.crscube.locale.domain.model.ApplicationLocale;
import ojt.crscube.locale.interfaces.dto.LocaleDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;
import static ojt.crscube.locale.domain.model.ApplicationLocale.values;

/**
 * Created by taesu at : 2019-04-17
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Component
public class LocaleSearchService {
    public List<LocaleDto.LocaleSearchResponse> searchLocales() {
        return Arrays.stream(values())
                     .sorted(comparingInt(ApplicationLocale::getOrder))
                     .map(LocaleDto::toResponse)
                     .collect(toList());
    }
}
