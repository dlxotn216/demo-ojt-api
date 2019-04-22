package ojt.crscube.board.application;

import lombok.RequiredArgsConstructor;
import ojt.crscube.board.domain.model.Board;
import ojt.crscube.board.domain.repository.BoardRepository;
import ojt.crscube.board.interfaces.dto.BoardDto.BoardSearchResponse;
import ojt.crscube.board.interfaces.dto.BoardDto.BoardUpdateRequest;
import org.springframework.stereotype.Component;

import static ojt.crscube.auth.AuthenticationContextHolder.getAuthentication;
import static ojt.crscube.base.utils.Messages.ILLEGAL_ACCESS;
import static ojt.crscube.board.interfaces.dto.BoardDto.BoardSearchResponse.from;

/**
 * Created by taesu at : 2019-04-22
 *
 * 게시판 수정 서비스
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Component @RequiredArgsConstructor
public class BoardUpdateService {
    private final BoardRepository boardRepository;
    private final BoardSearchService boardSearchService;

    public BoardSearchResponse updateBoard(Long key, BoardUpdateRequest request) {
        request.requestValidation();
        final Board board = this.boardSearchService.searchOrThrow(key);
        board.update(request.getSubject(), request.getContent(), request.getReason());

        if (!board.isWriter(getAuthentication().getKey())) {
            throw new IllegalStateException(ILLEGAL_ACCESS);
        }

        return from(this.boardRepository.save(board));
    }
}
