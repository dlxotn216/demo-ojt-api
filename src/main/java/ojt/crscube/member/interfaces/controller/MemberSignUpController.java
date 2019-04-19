package ojt.crscube.member.interfaces.controller;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.interfaces.dto.ApiResponse;
import ojt.crscube.member.application.MemberSignUpService;
import ojt.crscube.member.interfaces.dto.MemberDto.MemberSignUpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static ojt.crscube.base.interfaces.dto.ApiResponse.success;
import static org.springframework.http.ResponseEntity.status;

/**
 * Created by taesu at : 2019-04-19
 *
 * 사용자 생성 API Handler
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@RestController @RequiredArgsConstructor
public class MemberSignUpController {

    private final MemberSignUpService memberSignUpService;

    @PostMapping("/members/signup")
    public ResponseEntity<ApiResponse> createMember(@RequestBody MemberSignUpRequest request) {
        return status(HttpStatus.CREATED).body(success(this.memberSignUpService.createMember(request)));
    }
}
