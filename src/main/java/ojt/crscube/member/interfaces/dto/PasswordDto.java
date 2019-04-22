package ojt.crscube.member.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ojt.crscube.base.utils.Messages;

import java.util.Objects;

import static ojt.crscube.base.utils.Messages.REQUIRED_PARAMETER;
import static ojt.crscube.base.utils.VariableUtils.requireNonNull;

/**
 * Created by taesu at : 2019-04-22
 *
 * MemberPassword interfaces layer dto
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
public final class PasswordDto {
    private PasswordDto() {
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class PasswordChangeRequest {
        private String originPassword;
        private String newPassword;
        private String reason;

        public void requestValidation() {
            requireNonNull(originPassword, REQUIRED_PARAMETER);
            requireNonNull(newPassword, REQUIRED_PARAMETER);
        }
    }
}
