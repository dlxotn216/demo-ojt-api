package ojt.crscube.interceptor;

import ojt.crscube.member.domain.model.Member;
import ojt.crscube.token.application.service.TokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ojt.crscube.auth.AuthenticationContextHolder.remove;
import static ojt.crscube.auth.AuthenticationContextHolder.setAuthentication;
import static ojt.crscube.base.utils.Messages.ACCESS_TOKEN_INVALID;

/**
 * Created by taesu on 2019-04-19.
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    /**
     * AccessToken 체크를 skip 할 URI 와 Http Method 를 Key, Value 로
     * 저장하는 Immutable Map
     * <p>
     * 반드시 Base context 를 포함하는 full URI 가 Key 가 되어야 함
     * (mvc:interceptor 에서 처럼 wild card 사용은 지양)
     * <p>
     * 모든 HttpMethod 를 허용해야 할 경우 ALL 로 설정 하지 말고
     * context-common-{env}.xml 내에 선언 된 mvc:exclude-mapping 을 이용
     */
    private static final Map<String, List<String>> skipURIs;

    static {
        Map<String, List<String>> map = new HashMap<>();
        map.put("/locales", Collections.singletonList("GET"));
        map.put("/i18ns/entry", Collections.singletonList("GET"));
        map.put("/members/login", Collections.singletonList("POST"));
        map.put("/members/signup", Collections.singletonList("POST"));

        skipURIs = Collections.unmodifiableMap(map);
    }

    private TokenService<Member> tokenService;

    public AuthorizationInterceptor(@Qualifier("AccessTokenService") TokenService<Member> tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestUri = request.getRequestURI();
        String method = request.getMethod();

        if (isSkipURI(requestUri, method)) {
            return true;
        }

        String accessToken = resolveToken(request);
        if (StringUtils.isEmpty(accessToken)) {
            throw new IllegalStateException(ACCESS_TOKEN_INVALID);
        }

        setAuthentication(tokenService.parseTokenValue(accessToken));
        return true;
    }

    /**
     * http 요청에 대해 검증을 처리할 필요가 없는 지 조사
     *
     * @param requestURI    요청 URL
     * @param requestMethod 요청 Method
     */
    private boolean isSkipURI(String requestURI, String requestMethod) {
        List<String> allowedMethods = skipURIs.get(requestURI);
        return !CollectionUtils.isEmpty(allowedMethods) && (allowedMethods.contains(requestMethod));
    }


    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        remove();
    }
}
