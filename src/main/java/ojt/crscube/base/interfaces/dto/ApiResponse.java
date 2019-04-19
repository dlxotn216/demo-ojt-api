package ojt.crscube.base.interfaces.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static ojt.crscube.base.utils.MessageUtils.getI18nMessage;
import static ojt.crscube.base.utils.Messages.RESPONSE_SUCCESS;

/**
 * Created by taesu at : 2019-04-12
 *
 * Application에서 반환하는 Api의 공통 Response이다.
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiResponse {
    private Object result;
    private String message;

    public static ApiResponse success(Object result) {
        return success(result, getI18nMessage(RESPONSE_SUCCESS));
    }

    public static ApiResponse success(Object result, String message) {
        return new ApiResponse(result, message);
    }

    public static ApiResponse fail(String message) {
        return new ApiResponse("fail", getI18nMessage(message));
    }
}
