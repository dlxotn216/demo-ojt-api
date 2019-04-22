package ojt.crscube.member.interfaces.controller;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.domain.exception.EntityNotFoundException;
import ojt.crscube.base.interfaces.dto.ApiResponse;
import ojt.crscube.member.application.MemberSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static ojt.crscube.base.interfaces.dto.ApiResponse.success;
import static ojt.crscube.base.utils.Messages.MEMBER_AVAILABLE_ID;
import static ojt.crscube.base.utils.Messages.MEMBER_DUPLICATED_ID;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

/**
 * Created by taesu at : 2019-04-22
 *
 * 사용자 ID 중복체크 API Handler
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@RestController @RequiredArgsConstructor
public class DuplicatedMemberIdCheckController {

    private final MemberSearchService memberSearchService;

    @GetMapping("/members/duplicated-check")
    public ResponseEntity<ApiResponse> check(@RequestParam("id") String id) {
        try {
            this.memberSearchService.searchOrThrowById(id);
            return status(BAD_REQUEST).body(success("fail", MEMBER_DUPLICATED_ID));
        } catch (EntityNotFoundException e) {
            return ok(success("success", MEMBER_AVAILABLE_ID));
        }
    }
}
