package ojt.crscube.board.interfaces.controller;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.interfaces.dto.ApiResponse;
import ojt.crscube.board.application.BoardSearchService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static ojt.crscube.base.interfaces.dto.ApiResponse.success;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by taesu on 2019-04-22.
 */
@RestController @RequiredArgsConstructor
public class BoardSearchController {
    private final BoardSearchService boardSearchService;

    @GetMapping("/boards")
    public ResponseEntity<ApiResponse> search(
            @PageableDefault(sort = {"updateDateTime"}, direction = Sort.Direction.DESC, value = 5) Pageable pageable,
            @RequestParam(name = "criteria", required = false, defaultValue = "") String searchOptionString,
            @RequestParam(name = "condition", required = false, defaultValue = "and") String condition) {
        return ok(success(this.boardSearchService.searchBoards(searchOptionString, condition, pageable)));
    }

    @GetMapping("/boards/{key}")
    public ResponseEntity<ApiResponse> search(@PathVariable("key") Long key) {
        return ok(success(this.boardSearchService.searchOrThrow(key)));
    }
}
