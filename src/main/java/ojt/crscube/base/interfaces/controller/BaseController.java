package ojt.crscube.base.interfaces.controller;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.domain.exception.EntityNotFoundException;
import ojt.crscube.base.interfaces.dto.ApiResponse;
import ojt.crscube.i18n.domain.exception.UnSupportedLocaleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static ojt.crscube.base.interfaces.dto.ApiResponse.fail;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;

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

    @ExceptionHandler(value = {
            EntityNotFoundException.class
    })
    public ResponseEntity<ApiResponse> handle(EntityNotFoundException e) {
        return status(NOT_FOUND).body(fail((e.getMessage())));
    }
}
