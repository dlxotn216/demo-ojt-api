package ojt.crscube.board.interfaces.controller;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.interfaces.dto.ApiResponse;
import ojt.crscube.board.application.BoardUpdateService;
import ojt.crscube.board.interfaces.dto.BoardDto.BoardUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static ojt.crscube.base.interfaces.dto.ApiResponse.success;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by taesu at : 2019-04-22
 *
 * 게[시판 수정 API Handler
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@RestController @RequiredArgsConstructor
public class BoardUpdateController {
    private final BoardUpdateService boardUpdateService;

    @PutMapping("/boards/{key}")
    public ResponseEntity<ApiResponse> update(@PathVariable("key") Long key,
                                              @RequestBody BoardUpdateRequest request) {
        return ok(success(this.boardUpdateService.updateBoard(key, request)));
    }
}
