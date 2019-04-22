package ojt.crscube.member.interfaces.controller;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.interfaces.dto.ApiResponse;
import ojt.crscube.member.application.PasswordChangeService;
import ojt.crscube.member.interfaces.dto.PasswordDto.PasswordChangeRequest;
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
 * 비밀번호 변경 서비스 API Handler
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@RestController @RequiredArgsConstructor
public class PasswordChangeController {
    private final PasswordChangeService passwordChangeService;

    @PutMapping("/members/{key}/passwords")
    public ResponseEntity<ApiResponse> change(@PathVariable("key") Long key,
                                              @RequestBody PasswordChangeRequest request) {
        this.passwordChangeService.changePassword(key, request);
        return ok(success());
    }
}
