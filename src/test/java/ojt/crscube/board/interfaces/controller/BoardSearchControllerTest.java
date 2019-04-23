package ojt.crscube.board.interfaces.controller;

import ojt.crscube.base.domain.exception.EntityNotFoundException;
import ojt.crscube.board.domain.model.Board;
import ojt.crscube.board.domain.repository.BoardRepository;
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

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by taesu on 2019-04-22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class BoardSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void Board_리스트_조회_테스트() throws Exception {
        //given
        this.boardRepository.saveAll(Arrays.asList(
                Board.builder().subject("test01").content("test01 content").build(),
                Board.builder().subject("test02").content("test02 content").build(),
                Board.builder().subject("test03").content("test03 content").build(),
                Board.builder().subject("test04").content("test04 content").build(),
                Board.builder().subject("test05").content("test05 content").build(),
                Board.builder().subject("test06").content("test06 content").build()
        ));

        MockHttpServletRequestBuilder requestBuilder = get("/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //when
        ResultActions perform = this.mockMvc.perform(requestBuilder);

        //then
        perform.andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.result.content").isArray())
        ;
    }

    @Test
    public void Board_리스트_Filter_조회_테스트() throws Exception {
        //given
        this.boardRepository.saveAll(Arrays.asList(
                Board.builder().subject("test01").content("test01 content").build(),
                Board.builder().subject("test02").content("test02 content").build(),
                Board.builder().subject("test03").content("test03 content").build(),
                Board.builder().subject("test04").content("test04 content").build(),
                Board.builder().subject("test05").content("test05 content").build(),
                Board.builder().subject("test06").content("test06 content").build()
        ));

        MockHttpServletRequestBuilder requestBuilder = get("/boards?criteria=subject:3,updateDateTime>=2019-04-21")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //when
        ResultActions perform = this.mockMvc.perform(requestBuilder);

        //then
        perform.andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.result.content").isArray())
               .andExpect(jsonPath("$.result.totalElements").value(1))
        ;
        /*
         select board0_.board_key as board_ke1_0_0_, member1_.member_key as member_k1_6_1_, board0_.content as content2_0_0_,
                board0_.description as descript3_0_0_, board0_.reason as reason4_0_0_,
                board0_.update_datetime as update_d5_0_0_, board0_.updated_by as updated_7_0_0_,
                board0_.subject as subject6_0_0_, member1_.description as descript2_6_1_, member1_.reason as reason3_6_1_,
                member1_.update_datetime as update_d4_6_1_, member1_.updated_by as updated_7_6_1_,
                member1_.member_id as member_i5_6_1_, member1_.member_name as member_n6_6_1_
         from mst_board board0_ inner join mst_member member1_ on board0_.updated_by=member1_.member_key
         where ?=? and (lower(board0_.subject) like ? escape '!') and board0_.update_datetime>=?
         order by board0_.update_datetime desc limit ?

         binding parameter [1] as [BOOLEAN] - [true]
         binding parameter [2] as [BOOLEAN] - [true]
         binding parameter [3] as [VARCHAR] - [%3%]
         binding parameter [4] as [TIMESTAMP] - [2019-04-21T00:00]
         */
    }

    @Test
    public void Board_단건_조회_테스트() throws Exception {
        //given
        List<Board> boards = this.boardRepository.saveAll(Arrays.asList(
                Board.builder().subject("test01").content("test01 content").build(),
                Board.builder().subject("test02").content("test02 content").build(),
                Board.builder().subject("test03").content("test03 content").build(),
                Board.builder().subject("test04").content("test04 content").build(),
                Board.builder().subject("test05").content("test05 content").build(),
                Board.builder().subject("test06").content("test06 content").build()
        ));

        final Long targetKey = boards.get(0).getKey();
        MockHttpServletRequestBuilder requestBuilder = get("/boards/"+targetKey)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //when
        ResultActions perform = this.mockMvc.perform(requestBuilder);
        
        //then
        Board board = this.boardRepository.findById(targetKey).orElseThrow(EntityNotFoundException::new);

        perform.andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.result.subject").value(board.getSubject()))
               .andExpect(jsonPath("$.result.content").value(board.getContent()))
        ;
    }

    @Test
    public void Board_단건_조회_실패_테스트() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = get("/boards/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        //when
        ResultActions perform = this.mockMvc.perform(requestBuilder);

        //then
        perform.andDo(print())
               .andExpect(status().isNotFound())
        ;
    }
}