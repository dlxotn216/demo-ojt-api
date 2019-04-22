package ojt.crscube.i18n.interfaces.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Locale;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.of;
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

    @Test
    public void Locale_KO_다국어_조회_테스트() throws Exception {
        //given
        final String i18nIds = of("REQUIRED_PARAMETER", "ENTITY_NOT_FOUND").collect(joining(","));
        final String ko = "ko";
        /*
            HTTP Method = GET
            Request URI = /i18ns/entry
            Parameters = {i18nIds=[REQUIRED_PARAMETER,ENTITY_NOT_FOUND]}
            Headers = [Accept-Language:"ko"]
         */

        MockHttpServletRequestBuilder requestBuilder
                = get("/i18ns/entry?i18nIds=" + i18nIds).header("Accept-Language", ko);

        //when
        ResultActions perform = this.mockMvc.perform(requestBuilder);

        //then
        perform.andDo(print())
               .andExpect(jsonPath("$.result.REQUIRED_PARAMETER").value("필수 파라미터가 누락되었습니다."))
               .andExpect(jsonPath("$.result.ENTITY_NOT_FOUND").value("존재하지 않는 엔티티입니다."))
        ;


        /*---------------------------------------------------------------------------------------*/
        //given
        requestBuilder = get("/i18ns/entry?i18nIds=" + i18nIds).locale(Locale.KOREA);
        /*
            MockHttpServletRequest:
            HTTP Method = GET
            Request URI = /i18ns/entry
            Parameters = {i18nIds=[REQUIRED_PARAMETER,ENTITY_NOT_FOUND]}
            Headers = [Accept-Language:"ko-kr"]
         */

        //when
        ResultActions perform1 = this.mockMvc.perform(requestBuilder);


        //then
        perform1.andDo(print())
                .andExpect(jsonPath("$.result.REQUIRED_PARAMETER").value("필수 파라미터가 누락되었습니다."))
                .andExpect(jsonPath("$.result.ENTITY_NOT_FOUND").value("존재하지 않는 엔티티입니다."))
        ;

        /*---------------------------------------------------------------------------------------*/
        //given
        requestBuilder = get("/i18ns/entry?i18nIds=" + i18nIds).locale(Locale.KOREAN);
        /*
            HTTP Method = GET
            Request URI = /i18ns/entry
            Parameters = {i18nIds=[REQUIRED_PARAMETER,ENTITY_NOT_FOUND]}
            Headers = [Accept-Language:"ko"]
         */

        //when
        ResultActions perform2 = this.mockMvc.perform(requestBuilder);

        //then
        perform2.andDo(print())
                .andExpect(jsonPath("$.result.REQUIRED_PARAMETER").value("필수 파라미터가 누락되었습니다."))
                .andExpect(jsonPath("$.result.ENTITY_NOT_FOUND").value("존재하지 않는 엔티티입니다."))
        ;
    }

    @Test
    public void Locale_EN_다국어_조회_테스트() throws Exception {
        //given
        final String i18nIds = of("REQUIRED_PARAMETER", "ENTITY_NOT_FOUND").collect(joining(","));
        final String en = "en";
        /*
            HTTP Method = GET
            Request URI = /i18ns/entry
            Parameters = {i18nIds=[REQUIRED_PARAMETER,ENTITY_NOT_FOUND]}
            Headers = [Accept-Language:"en"]
         */

        MockHttpServletRequestBuilder requestBuilder
                = get("/i18ns/entry?i18nIds=" + i18nIds).header("Accept-Language", en);

        //when
        ResultActions perform = this.mockMvc.perform(requestBuilder);

        //then
        perform.andDo(print())
               .andExpect(jsonPath("$.result.REQUIRED_PARAMETER").value("Missing required parameters."))
               .andExpect(jsonPath("$.result.ENTITY_NOT_FOUND").value("Entity does not exist."))
        ;


        /*---------------------------------------------------------------------------------------*/
        //given
        requestBuilder = get("/i18ns/entry?i18nIds=" + i18nIds).locale(Locale.ENGLISH);
        /*
            MockHttpServletRequest:
            HTTP Method = GET
            Request URI = /i18ns/entry
            Parameters = {i18nIds=[REQUIRED_PARAMETER,ENTITY_NOT_FOUND]}
            Headers = [Accept-Language:"en-kr"]
         */

        //when
        ResultActions perform1 = this.mockMvc.perform(requestBuilder);


        //then
        perform1.andDo(print())
                .andExpect(jsonPath("$.result.REQUIRED_PARAMETER").value("Missing required parameters."))
                .andExpect(jsonPath("$.result.ENTITY_NOT_FOUND").value("Entity does not exist."))
        ;
    }
}