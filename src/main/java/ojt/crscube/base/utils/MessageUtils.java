package ojt.crscube.base.utils;

import ojt.crscube.i18n.application.I18nLabelSearchService;

/**
 * Created by taesu on 2019-04-20.
 */
public final class MessageUtils {
    private MessageUtils() {

    }

    public static String getI18nMessage(String i18nId) {
        return StaticContextAccessor.getBean(I18nLabelSearchService.class).searchOrDefault(i18nId);
    }
}
