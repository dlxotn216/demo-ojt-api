package ojt.crscube.board.interfaces.controller;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.interfaces.dto.ApiResponse;
import ojt.crscube.board.application.BoardCreateService;
import ojt.crscube.board.interfaces.dto.BoardDto.BoardCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static ojt.crscube.base.interfaces.dto.ApiResponse.success;
import static org.springframework.http.ResponseEntity.status;

/**
 * Created by taesu on 2019-04-21.
 *
 * Bord 생성 API Handler
 */
@RestController @RequiredArgsConstructor
public class BoardCreateController {
    private final BoardCreateService boardCreateService;

    @PostMapping("/boards")
    public ResponseEntity<ApiResponse> create(@RequestBody BoardCreateRequest request) {
        return status(HttpStatus.CREATED).body(success(this.boardCreateService.createBoard(request)));
    }
}
