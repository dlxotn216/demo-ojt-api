package ojt.crscube.auth;

import org.junit.Test;

import java.lang.reflect.Constructor;

/**
 * Created by Lee Tae Su on 2019-04-25.
 */
public class AuthenticationContextHolderTest {
    @Test
    public void 생성자_테스트() throws Exception {
        for (Constructor<?> constructor : AuthenticationContextHolder.class.getDeclaredConstructors()) {
            constructor.setAccessible(true);
            constructor.newInstance();
        }
        AuthenticationContextHolder.remove();
    }

}