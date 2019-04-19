package ojt.crscube.i18n.interfaces.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by taesu on 2019-04-20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class I18nSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void entry_조회_테스트() throws Exception {
        this.mockMvc.perform(get("/i18ns/entry"))
                .andDo(print())
                .andExpect(jsonPath("$.result").exists())
        ;
    }

}