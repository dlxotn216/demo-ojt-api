package ojt.crscube.i18n.interfaces.controller;

import ojt.crscube.i18n.domain.exception.UnSupportedLocaleException;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class I18nCreateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 다국어_생성_테스트() throws Exception {
        RequestBuilder builder = post("/i18ns")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"i18nId\" : \"USER.USER_NAME\",  "
                        + "\"localeLabelMap\": { \"en\":\"Username\", \"ko\":\"사용자 이름\" } }");
        this.mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.result.i18nId").value("USER.USER_NAME"))
                .andExpect(jsonPath("$.result.localeLabelMap.en").value("Username"))
                .andExpect(jsonPath("$.result.localeLabelMap.ko").value("사용자 이름"))

        ;
    }

    @Test
    public void 지원하지_않는_Locale_다국어_생성_테스트() throws Exception {
        //given
        RequestBuilder builder = post("/i18ns")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"i18nId\" : \"USER.USER_NAME\",  "
                        + "\"localeLabelMap\": { \"en\":\"Username\", \"ko\":\"사용자 이름\" , "
                        + "\"eu\":\"사용자 이름\", \"cn\":\"사용자 이름\"  } }");

        //when
        ResultActions perform = this.mockMvc.perform(builder);

        //then
        perform.andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void 중복된_Locale_다국어_생성_테스트() throws Exception {
        RequestBuilder builder = post("/i18ns")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"i18nId\" : \"USER.USER_NAME\",  "
                        + "\"localeLabelMap\": { \"en\":\"Username\", \"ko\":\"사용자 이름\" , "
                        + "\"ko\":\"사용자 이름\", \"en\":\"사용자 이름\"  } }");

        this.mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isCreated())
        ;
    }

    @Test
    public void 기본_다국어_등록_테스트() throws Exception {
        RequestBuilder builder = post("/i18ns")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"i18nId\" : \"USER.USER_NAME\",  "
                        + "\"localeLabelMap\": { \"en\":\"Username\", \"ko\": null} }");

        this.mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.result.localeLabelMap.ko").exists())
                .andExpect(jsonPath("$.result.localeLabelMap.ko").value("Username"))
        ;
    }

}

