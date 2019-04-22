package ojt.crscube.member.interfaces.controller;

import ojt.crscube.member.application.MemberSignUpService;
import ojt.crscube.member.interfaces.dto.MemberDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Lee Tae Su on 2019-04-22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class DuplicatedMemberIdCheckControllerTest {
    @Autowired
    private MemberSignUpService memberSignUpService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 사용자_아이디_중복체크_테스트() throws Exception {
        //given
        MemberDto.MemberSearchResponse testuser = this.memberSignUpService.createMember(
                new MemberDto.MemberSignUpRequest("testuser", "test user", "test01!@#", "test01!@#"));

        MockHttpServletRequestBuilder getRequest = get("/members/duplicated-check?id=testuser")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        //when
        ResultActions perform = this.mockMvc.perform(getRequest);

        //then
        perform.andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.result").value("fail"))
        ;


        getRequest = get("/members/duplicated-check?id=testusersaa")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        //when
        perform = this.mockMvc.perform(getRequest);

        //then
        perform.andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.result").value("success"))
        ;

    }
}