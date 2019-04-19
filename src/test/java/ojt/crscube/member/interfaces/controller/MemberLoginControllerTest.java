package ojt.crscube.member.interfaces.controller;

import ojt.crscube.member.application.MemberSignUpService;
import ojt.crscube.member.interfaces.dto.MemberDto.MemberSignUpRequest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by taesu on 2019-04-19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class MemberLoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberSignUpService memberSignUpService;

    @Test
    public void 로그인_테스트() throws Exception {
        //given
        final String id = "taesu";
        final String name = "Lee Tae Su";
        final String password = "password1!";

        this.memberSignUpService.createMember(new MemberSignUpRequest(id, name, password, password));

        MockHttpServletRequestBuilder post = post("/members/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"id\": \"taesu\",\n" +
                        "  \"password\": \"password1!\" \n" +
                        "}");

        //when
        ResultActions perform = this.mockMvc.perform(post);


        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value("taesu"))
                .andExpect(jsonPath("$.result.name").value("Lee Tae Su"))
                .andExpect(jsonPath("$.result.accessToken").exists())
        ;
    }

    @Test
    public void 로그인_실패_테스트() throws Exception {
        //given
        final String id = "taesu";
        final String name = "Lee Tae Su";
        final String password = "password1!";

        this.memberSignUpService.createMember(new MemberSignUpRequest(id, name, password, password));

        MockHttpServletRequestBuilder post = post("/members/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"id\": \"taesu\",\n" +
                        "  \"password\": \"passwo1231232rd1!\" \n" +
                        "}");

        //when
        ResultActions perform = this.mockMvc.perform(post);

        //then
        perform.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value("fail"))
                .andExpect(jsonPath("$.message").exists())
        ;
    }
}