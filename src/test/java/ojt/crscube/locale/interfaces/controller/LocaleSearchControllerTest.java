package ojt.crscube.locale.interfaces.controller;

import ojt.crscube.locale.domain.model.ApplicationLocale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LocaleSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void Locale_목록_조회_api_테스트() throws Exception {
        MockHttpServletRequestBuilder requestBuilder
                = MockMvcRequestBuilders.get("/locales")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON);

        ApplicationLocale[] values = ApplicationLocale.values();

        this.mockMvc.perform(requestBuilder)
                    .andDo(print())
                    .andExpect(jsonPath("$.result[0].locale").value(values[0].getLocale().toString()))
                    .andExpect(jsonPath("$.result[1].locale").value(values[1].getLocale().toString()))
        ;
    }
}