package ojt.crscube.base.utils;

import org.junit.Test;

import java.lang.reflect.Constructor;

/**
 * Created by Lee Tae Su on 2019-04-25.
 */
public class EncryptionUtilsTest {

    @Test
    public void 생성자_테스트() throws Exception {
        for (Constructor<?> constructor : EncryptionUtils.class.getDeclaredConstructors()) {
            constructor.setAccessible(true);
            constructor.newInstance();
        }
    }

}