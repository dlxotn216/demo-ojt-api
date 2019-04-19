package ojt.crscube.member.domain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Lee Tae Su on 2019-04-19.
 */
public class MemberPasswordTest {

    @Test
    public void 비밀번호_생성_테스트() {
        //given and when
        MemberPassword memberPassword = MemberPassword.createNewPassword(null, "memberPassword?12");

        //then
        assertThat(memberPassword.getEncryptedPassword()).isNotEqualTo("memberPassword?12");
        System.out.println(memberPassword.getEncryptedPassword());
    }

    @Test(expected = IllegalArgumentException.class)
    public void INVALID_비밀번호_생성_테스트() {
        //given and when
        MemberPassword memberPassword = MemberPassword.createNewPassword(null, "password12");
        memberPassword = MemberPassword.createNewPassword(null, "pw?12");

        //then
        //will be throw exception
        assertThat(1).isEqualTo(2);
    }

    @Test
    public void 비밀번호_매칭_테스트() {
        //given 
        MemberPassword memberPassword = MemberPassword.createNewPassword(null, "memberPassword?12");

        //when
        boolean isMatched = memberPassword.isMatchedPassword("memberPassword?12");

        //then
        assertThat(isMatched).isTrue();
    }

    @Test
    public void 비밀번호_None매칭_테스트() {
        //given 
        MemberPassword memberPassword = MemberPassword.createNewPassword(null, "memberPassword?12");

        //when
        boolean isMatched = memberPassword.isMatchedPassword("123243213");

        //then
        assertThat(isMatched).isFalse();
    }
}