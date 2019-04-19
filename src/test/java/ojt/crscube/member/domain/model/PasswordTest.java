package ojt.crscube.member.domain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Lee Tae Su on 2019-04-19.
 */
public class PasswordTest {

    @Test
    public void 비밀번호_생성_테스트() {
        //given and when
        Password password = Password.createNewPassword(null, "password?12");

        //then
        assertThat(password.getEncryptedPassword()).isNotEqualTo("password?12");
        System.out.println(password.getEncryptedPassword());
    }

    @Test(expected = IllegalArgumentException.class)
    public void INVALID_비밀번호_생성_테스트() {
        //given and when
        Password password = Password.createNewPassword(null, "password12");
        password = Password.createNewPassword(null, "pw?12");

        //then
        //will be throw exception
    }

    @Test
    public void 비밀번호_매칭_테스트() {
        //given 
        Password password = Password.createNewPassword(null, "password?12");

        //when
        boolean isMatched = password.isMatchedPassword("password?12");

        //then
        assertThat(isMatched).isTrue();
    }

    @Test
    public void 비밀번호_None매칭_테스트() {
        //given 
        Password password = Password.createNewPassword(null, "password?12");

        //when
        boolean isMatched = password.isMatchedPassword("123243213");

        //then
        assertThat(isMatched).isFalse();
    }
}