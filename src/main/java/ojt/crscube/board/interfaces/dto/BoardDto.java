package ojt.crscube.board.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ojt.crscube.board.domain.model.Board;

import java.time.LocalDate;

import static ojt.crscube.base.utils.Messages.REQUIRED_PARAMETER;
import static ojt.crscube.base.utils.VariableUtils.requireNonNull;
import static org.springframework.util.StringUtils.trimWhitespace;

/**
 * Created by taesu on 2019-04-21.
 */
public final class BoardDto {
    private BoardDto() {
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class BoardCreateRequest {
        private String subject;
        private String content;

        public void setSubject(String subject) {
            this.subject = trimWhitespace(subject);
        }

        public void setContent(String content) {
            this.content = trimWhitespace(content);
        }

        public void requestValidation() {
            requireNonNull(subject, REQUIRED_PARAMETER);
            requireNonNull(content, REQUIRED_PARAMETER);
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class BoardsSearchResponse {
        private Long no;
        private Long key;
        private String subject;
        private LocalDate updatedDate;
        private String updatedBy;
        private boolean deleted;

        public BoardsSearchResponse(Long key, String subject, LocalDate updatedDate, String updatedBy,
                                    boolean deleted) {
            this.key = key;
            this.subject = subject;
            this.updatedDate = updatedDate;
            this.updatedBy = updatedBy;
            this.deleted = deleted;
        }

        public static BoardsSearchResponse from(Board board) {
            return new BoardsSearchResponse(
                    board.getKey(),
                    board.getSubject(),
                    board.getUpdatedDateTime().toLocalDate(),
                    board.getWriter().getId(),
                    board.getEntityBase().getDeleted());
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class BoardSearchResponse {
        private Long key;
        private String subject;
        private String content;
        private LocalDate updatedDate;
        private String updatedBy;

        public static BoardSearchResponse from(Board board) {
            return new BoardSearchResponse(
                    board.getKey(),
                    board.getSubject(),
                    board.getContent(),
                    board.getUpdatedDateTime().toLocalDate(),
                    board.getWriter().getId());
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class BoardUpdateRequest {
        private String subject;
        private String content;
        private String reason;

        public void setSubject(String subject) {
            this.subject = trimWhitespace(subject);
        }

        public void setContent(String content) {
            this.content = trimWhitespace(content);
        }

        public void requestValidation() {
            requireNonNull(subject, REQUIRED_PARAMETER);
            requireNonNull(content, REQUIRED_PARAMETER);
        }
    }
}
