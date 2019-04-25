package ojt.crscube.i18n.interfaces.dto;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.Collections;

/**
 * Created by Lee Tae Su on 2019-04-25.
 */
public class I18nDtoTest {
    @Test
    public void 생성자_테스트() throws Exception {
        for (Constructor<?> constructor : I18nDto.class.getDeclaredConstructors()) {
            constructor.setAccessible(true);
            constructor.newInstance();
        }

        new I18nDto.I18nCreateRequest("test", Collections.emptyMap());
        new I18nDto.I18nCreateResponse("test", Collections.emptyMap());
    }
}