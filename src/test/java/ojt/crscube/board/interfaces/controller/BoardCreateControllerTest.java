package ojt.crscube.board.interfaces.controller;

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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by taesu on 2019-04-21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class BoardCreateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void Board_생성_테스트() throws Exception {
        //given
        RequestBuilder builder = post("/boards")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"subject\": \"Test board 01\",\n" +
                        "  \"content\": \"Contents of Test board 01\"\n" +
                        "}");

        //when
        ResultActions perform = this.mockMvc.perform(builder);

        //then
        perform.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.result.key").exists())
                .andExpect(jsonPath("$.result.subject").value("Test board 01"))
                .andExpect(jsonPath("$.result.content").value("Contents of Test board 01"))

        ;
    }
}