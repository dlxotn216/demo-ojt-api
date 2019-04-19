package ojt.crscube.base.interfaces.controller;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.interfaces.dto.ApiResponse;
import ojt.crscube.i18n.domain.exception.UnSupportedLocaleException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static ojt.crscube.base.interfaces.dto.ApiResponse.fail;
import static org.springframework.http.ResponseEntity.badRequest;

/**
 * Created by taesu on 2019-04-19.
 */
@ControllerAdvice @RequiredArgsConstructor
public class BaseController {
    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            IllegalStateException.class,
            UnSupportedLocaleException.class
    })
    public ResponseEntity<ApiResponse> handle(RuntimeException e) {
        return badRequest().body(fail((e.getMessage())));
    }
}
