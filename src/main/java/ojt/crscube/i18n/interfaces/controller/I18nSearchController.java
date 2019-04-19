package ojt.crscube.i18n.interfaces.controller;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.interfaces.dto.ApiResponse;
import ojt.crscube.i18n.application.I18nSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static ojt.crscube.base.interfaces.dto.ApiResponse.success;
import static org.springframework.http.ResponseEntity.ok;

/**
 * I18n 목록 조회 API Handler
 */
@RestController @RequiredArgsConstructor
public class I18nSearchController {

    private final I18nSearchService i18nSearchService;

    @GetMapping("/i18ns/entry")
    public ResponseEntity<ApiResponse> searchAllI18nsByEntry(
            @RequestParam(value = "i18nIds", required = false) List<String> i18nIds) {
        return ok(success(this.i18nSearchService.searchI18nsByEntry(i18nIds)));
    }
}
