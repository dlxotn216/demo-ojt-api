package ojt.crscube.member.interfaces.controller;

import ojt.crscube.i18n.domain.exception.UnSupportedLocaleException;
import ojt.crscube.member.domain.model.Member;
import ojt.crscube.member.domain.repository.MemberRepository;
import ojt.crscube.member.domain.repository.PasswordRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Lee Tae Su on 2019-04-19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberCreateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @Test
    public void 사용자_생성_테스트() throws Exception {
        //given
        MockHttpServletRequestBuilder post = post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                                 "    \"id\": \"taesu\",\n" +
                                 "    \"name\": \"Lee Tae Su\",\n" +
                                 "    \"password\": \"password1!\",\n" +
                                 "    \"passwordConfirm\": \"password1!\"\n" +
                                 "  }");

        //when
        ResultActions perform = this.mockMvc.perform(post);

        //then
        perform.andDo(print())
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.result.key").exists())
               .andExpect(jsonPath("$.result.id").value("taesu"))
               .andExpect(jsonPath("$.result.name").value("Lee Tae Su"))
        ;

        Member taesu = this.memberRepository.findById("taesu").orElseThrow(IllegalStateException::new);
        assertThat(this.passwordRepository.findByMember(taesu).getEncryptedPassword()).isNotNull();
    }

    @Test
    public void 사용자_아이디_중복_생성_테스트() throws Exception {
        //given
        this.memberRepository.save(Member.builder().id("taesu").name("testuser").build());

        //When
        MockHttpServletRequestBuilder post = post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                                 "    \"id\": \"taesu\",\n" +
                                 "    \"name\": \"Lee Tae Su\",\n" +
                                 "    \"password\": \"password1!\",\n" +
                                 "    \"passwordConfirm\": \"password1!\"\n" +
                                 "  }");
        //then
        assertThatThrownBy(() -> this.mockMvc.perform(post)
                                             .andDo(print())
                                             .andExpect(status().isBadRequest()))
                .hasCause(new IllegalArgumentException("MEMBER.DUPLICATED_ID"))
        ;
    }

}