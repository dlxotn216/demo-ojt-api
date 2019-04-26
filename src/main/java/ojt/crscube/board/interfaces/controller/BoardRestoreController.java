package ojt.crscube.board.interfaces.controller;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.interfaces.dto.ApiResponse;
import ojt.crscube.board.application.BoardRestoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.noContent;

/**
 * Created by taesu at : 2019-04-26
 *
 * 게시판 복원 API Handler
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@RestController @RequiredArgsConstructor
public class BoardRestoreController {
    private final BoardRestoreService boardRestoreService;

    @PutMapping("/boards/{key}/restore")
    public ResponseEntity<ApiResponse> restore(@PathVariable("key") Long key,
                                               @RequestParam("reason") String reason) {
        this.boardRestoreService.restoreBoard(key, reason);
        return noContent().build();
    }
}
