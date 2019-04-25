package ojt.crscube.member.interfaces.dto;

import org.junit.Test;

import java.lang.reflect.Constructor;

/**
 * Created by Lee Tae Su on 2019-04-25.
 */
public class PasswordDtoTest {

    @Test
    public void 생성자_테스트() throws Exception {
        for (Constructor<?> constructor : PasswordDto.class.getDeclaredConstructors()) {
            constructor.setAccessible(true);
            constructor.newInstance();
        }

        new PasswordDto.PasswordChangeRequest("test", "test", "atew");
    }

}