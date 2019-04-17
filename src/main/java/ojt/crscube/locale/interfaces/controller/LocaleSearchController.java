package ojt.crscube.locale.interfaces.controller;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.interfaces.dto.ApiResponse;
import ojt.crscube.locale.application.LocaleSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static ojt.crscube.base.interfaces.dto.ApiResponse.success;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by taesu at : 2019-04-17
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
public class LocaleSearchController {

    private final LocaleSearchService localeSearchService;

    @GetMapping("/locales")
    public ResponseEntity<ApiResponse> searchLocales() {
        return ok(success(this.localeSearchService.searchLocales()));
    }
}
