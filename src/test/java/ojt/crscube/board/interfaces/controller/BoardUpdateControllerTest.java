package ojt.crscube.board.interfaces.controller;

import ojt.crscube.board.domain.model.Board;
import ojt.crscube.board.domain.repository.BoardRepository;
import ojt.crscube.member.domain.model.Member;
import ojt.crscube.member.domain.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import static ojt.crscube.auth.AuthenticationContextHolder.setAuthentication;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
public class BoardUpdateControllerTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void Board_수정_테스트() throws Exception {
        //given
        Board test01 = this.boardRepository.save(Board.builder().subject("test01").content("test01 content").build());
        
        setAuthentication(test01.getWriter());

        //when
        RequestBuilder builder = put("/boards/" + test01.getKey())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                                 "  \"subject\": \"Test board 01 changed\",\n" +
                                 "  \"content\": \"Contents of Test board 01 changed\",\n" +
                                 "  \"reason\": \"reason for update\"\n" +
                                 "}");

        //then
        this.mockMvc.perform(builder)
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result.subject").value("Test board 01 changed"))
                    .andExpect(jsonPath("$.result.content").value("Contents of Test board 01 changed"))
        ;
        assertThat(this.boardRepository.findById(test01.getKey()).orElseThrow(IllegalStateException::new)
                                       .getEntityBase().getReason()).isEqualTo("reason for update");
    }

    @Test
    public void Board_다른_사용자_수정_테스트() throws Exception {
        //given
        Board test01 = this.boardRepository.save(Board.builder().subject("test01").content("test01 content").build());

        setAuthentication(this.memberRepository.save(Member.createNewMember("another", "another", "password12?!@")));

        //when
        RequestBuilder builder = put("/boards/" + test01.getKey())
                .locale(Locale.KOREAN)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                                 "  \"subject\": \"Test board 01 changed\",\n" +
                                 "  \"content\": \"Contents of Test board 01 changed\"\n" +
                                 "}");

        //then
        this.mockMvc.perform(builder)
                    .andDo(print())
                    .andExpect(status().isBadRequest())
        ;
    }

}