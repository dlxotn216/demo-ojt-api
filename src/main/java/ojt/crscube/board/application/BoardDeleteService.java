package ojt.crscube.board.application;

import lombok.RequiredArgsConstructor;
import ojt.crscube.board.domain.model.Board;
import ojt.crscube.board.domain.repository.BoardRepository;
import org.springframework.stereotype.Component;

import static ojt.crscube.auth.AuthenticationContextHolder.getAuthentication;
import static ojt.crscube.base.utils.Messages.ILLEGAL_ACCESS;

/**
 * Created by taesu at : 2019-04-22
 *
 * 게시판 삭제 서비스
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Component @RequiredArgsConstructor
public class BoardDeleteService {
    private final BoardRepository boardRepository;
    private final BoardSearchService boardSearchService;

    public void deleteBoard(Long boardKey, String reason) {
        Board board = this.boardSearchService.searchOrThrow(boardKey).delete(reason);
        if (!board.isWriter(getAuthentication().getKey())) {
            throw new IllegalStateException(ILLEGAL_ACCESS);
        }
        
        this.boardRepository.save(board);
    }
}
