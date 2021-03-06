package ojt.crscube.member.interfaces.controller;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.interfaces.dto.ApiResponse;
import ojt.crscube.member.application.MemberLoginService;
import ojt.crscube.member.interfaces.dto.MemberDto.MemberLoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static ojt.crscube.base.interfaces.dto.ApiResponse.success;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by taesu at : 2019-04-19
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@RestController @RequiredArgsConstructor
public class MemberLoginController {

    private final MemberLoginService memberLoginService;

    @PostMapping("/members/login")
    public ResponseEntity<ApiResponse> login(@RequestBody MemberLoginRequest request) {
        return ok(success(this.memberLoginService.login(request)));
    }
}
