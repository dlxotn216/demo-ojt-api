package ojt.crscube.board.interfaces.controller;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.interfaces.dto.ApiResponse;
import ojt.crscube.board.application.BoardDeleteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.noContent;

/**
 * Created by taesu at : 2019-04-22
 *
 * 게[시판 삭제 API Handler
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@RestController @RequiredArgsConstructor
public class BoardDeleteController {
    private final BoardDeleteService boardDeleteService;

    @DeleteMapping("/boards/{key}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("key") Long key,
                                              @RequestParam("reason") String reason) {
        this.boardDeleteService.deleteBoard(key, reason);
        return noContent().build();
    }
}
