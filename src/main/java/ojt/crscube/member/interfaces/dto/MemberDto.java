package ojt.crscube.member.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ojt.crscube.member.domain.model.Member;

import java.util.Objects;

import static ojt.crscube.base.utils.Messages.PASSWORD_NOT_MATCHED;
import static ojt.crscube.base.utils.Messages.REQUIRED_PARAMETER;
import static ojt.crscube.base.utils.VariableUtils.requireNonNull;

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
    public static class MemberSignUpRequest {
        private String id;
        private String name;
        private String password;
        private String passwordConfirm;

        public void requestValidation() {
            requireNonNull(id, REQUIRED_PARAMETER);
            requireNonNull(name, REQUIRED_PARAMETER);
            requireNonNull(password, REQUIRED_PARAMETER);
            requireNonNull(passwordConfirm, REQUIRED_PARAMETER);

            if (!Objects.equals(password, passwordConfirm)) {
                throw new IllegalArgumentException(PASSWORD_NOT_MATCHED);
            }
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class MemberSignUpResponse {
        private Long key;
        private String id;
        private String name;

        public static MemberSignUpResponse from(Member member) {
            return new MemberSignUpResponse(member.getKey(), member.getId(), member.getName());
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class MemberLoginRequest {
        private String id;
        private String password;

        public void requestValidation() {
            Objects.requireNonNull(id, REQUIRED_PARAMETER);
            Objects.requireNonNull(password, REQUIRED_PARAMETER);
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class MemberLoginResponse {
        private String id;
        private String name;
        private String accessToken;

        public static MemberLoginResponse from(Member member, String accessToken) {
            return new MemberLoginResponse(member.getId(), member.getName(), accessToken);
        }
    }

}
