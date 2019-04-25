package ojt.crscube.locale.interfaces.dto;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.Locale;

/**
 * Created by Lee Tae Su on 2019-04-25.
 */
public class LocaleDtoTest {
    @Test
    public void 생성자_테스트() throws Exception {
        for (Constructor<?> constructor : LocaleDto.class.getDeclaredConstructors()) {
            constructor.setAccessible(true);
            constructor.newInstance();
        }

        new LocaleDto.LocaleSearchResponse(Locale.KOREA, "test");
    }

}