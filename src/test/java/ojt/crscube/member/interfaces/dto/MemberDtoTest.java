package ojt.crscube.member.interfaces.dto;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Lee Tae Su on 2019-04-25.
 */
public class MemberDtoTest {

    @Test
    public void private_생성자_테스트() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        for (Constructor<?> constructor : MemberDto.class.getDeclaredConstructors()) {
            constructor.setAccessible(true);
            constructor.newInstance();
        }
    }

}