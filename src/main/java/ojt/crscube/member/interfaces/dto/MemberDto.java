package ojt.crscube.member.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ojt.crscube.member.domain.model.Member;

import java.util.Objects;

/**
 * Created by taesu at : 2019-04-19
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
public final class MemberDto {
    private MemberDto() {
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class MemberCreateRequest {
        private String id;
        private String name;
        private String password;
        private String passwordConfirm;

        public void requestValidation() {
            Objects.requireNonNull(id, "REQUIRED_PARAMETER");
            Objects.requireNonNull(name, "REQUIRED_PARAMETER");
            Objects.requireNonNull(password, "REQUIRED_PARAMETER");
            Objects.requireNonNull(passwordConfirm, "REQUIRED_PARAMETER");

            if (!Objects.equals(password, passwordConfirm)) {
                throw new IllegalArgumentException("PASSWORD.NOT_MATCHED");
            }
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class MemberCreateResponse {
        private Long key;
        private String id;
        private String name;

        public static MemberCreateResponse from(Member member) {
            return new MemberCreateResponse(member.getKey(), member.getId(), member.getName());
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class MemberLoginRequest {
        private String id;
        private String password;
        public void requestValidation() {
            Objects.requireNonNull(id, "REQUIRED_PARAMETER");
            Objects.requireNonNull(password, "REQUIRED_PARAMETER");
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class MemberLoginResponse {
        private String id;
        private String name;
        private String accessToken;
    }
}
