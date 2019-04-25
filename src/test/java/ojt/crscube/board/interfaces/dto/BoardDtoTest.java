package ojt.crscube.board.interfaces.dto;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.time.LocalDate;

/**
 * Created by Lee Tae Su on 2019-04-25.
 */
public class BoardDtoTest {


    @Test
    public void 생성자_테스트() throws Exception {
        for (Constructor<?> constructor : BoardDto.class.getDeclaredConstructors()) {
            constructor.setAccessible(true);
            constructor.newInstance();
        }

        new BoardDto.BoardCreateRequest("awefwaf", "Awefawf");
        new BoardDto.BoardsSearchResponse(1L, "awet", LocalDate.now(), "awef", false);
        new BoardDto.BoardsSearchResponse(1L, 2L, "Awef", LocalDate.now(), "awef", false);
    }

}