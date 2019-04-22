package ojt.crscube.member.interfaces.controller;

import ojt.crscube.member.application.MemberLoginService;
import ojt.crscube.member.application.MemberSignUpService;
import ojt.crscube.member.domain.repository.MemberRepository;
import ojt.crscube.member.interfaces.dto.MemberDto.MemberLoginRequest;
import ojt.crscube.member.interfaces.dto.MemberDto.MemberLoginResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static ojt.crscube.base.utils.Messages.ENTITY_NOT_FOUND;
import static ojt.crscube.member.interfaces.dto.MemberDto.MemberSignUpRequest;
import static ojt.crscube.member.interfaces.dto.MemberDto.MemberSignUpResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Lee Tae Su on 2019-04-22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PasswordChangeControllerTest {

    @Autowired
    private MemberSignUpService memberSignUpService;

    @Autowired
    private MemberLoginService memberLoginService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 비밀번호_변경_테스트() throws Exception {
        //given
        MemberSignUpResponse testuser = this.memberSignUpService.createMember(
                new MemberSignUpRequest("testuser", "test user", "test01!@#", "test01!@#"));

        MockHttpServletRequestBuilder put = put("/members/" + testuser.getKey() + "/passwords")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                                 "  \"originPassword\": \"test01!@#\",\n" +
                                 "  \"newPassword\": \"testchanged12!@#\",\n" +
                                 "  \"reason\": \"for test\"\n" +
                                 "}");

        //when
        ResultActions perform = this.mockMvc.perform(put);

        //then
        perform.andDo(print())
               .andExpect(status().isOk());

        MemberLoginResponse response = this.memberLoginService
                .login(new MemberLoginRequest("testuser", "testchanged12!@#"));

        assertThat(response.getId()).isEqualTo("testuser");
        assertThat(response.getName()).isEqualTo("test user");
        assertThat(response.getAccessToken()).isNotNull();

        assertThat(this.memberRepository.findById(testuser.getKey())
                                        .orElseThrow(() -> new IllegalArgumentException(ENTITY_NOT_FOUND))
                                        .getMemberPassword()
                                        .orElseThrow(() -> new IllegalArgumentException(ENTITY_NOT_FOUND))
                                        .getEntityBase().getReason()).isEqualTo("for test");

    }
}