package ojt.crscube.i18n.interfaces.controller;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.interfaces.dto.ApiResponse;
import ojt.crscube.i18n.application.I18nCreateService;
import ojt.crscube.i18n.interfaces.dto.I18nDto.I18nCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static ojt.crscube.base.interfaces.dto.ApiResponse.success;
import static org.springframework.http.ResponseEntity.status;

/**
 * Created by taesu at : 2019-04-17
 *
 * 다국어 생성 API Handler
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
public class I18nCreateController {

    private final I18nCreateService i18nCreateService;

    @PostMapping("/i18ns")
    public ResponseEntity<ApiResponse> createI18n(@RequestBody I18nCreateRequest request) {
        return status(HttpStatus.CREATED).body(success(this.i18nCreateService.createI18n(request)));
    }
    
}
