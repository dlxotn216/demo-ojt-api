package ojt.crscube.board.application;

import lombok.RequiredArgsConstructor;
import ojt.crscube.board.domain.model.Board;
import ojt.crscube.board.domain.repository.BoardRepository;
import ojt.crscube.board.interfaces.dto.BoardDto.BoardCreateRequest;
import ojt.crscube.board.interfaces.dto.BoardDto.BoardSearchResponse;
import org.springframework.stereotype.Component;

import static ojt.crscube.board.interfaces.dto.BoardDto.BoardSearchResponse.from;

/**
 * Created by taesu on 2019-04-21.
 */
@Component @RequiredArgsConstructor
public class BoardCreateService {
    private final BoardRepository boardRepository;

    public BoardSearchResponse createBoard(BoardCreateRequest request) {
        request.requestValidation();

        final Board savedBoard = this.boardRepository.save(Board.builder().subject(request.getSubject())
                .content(request.getContent()).build());

        return from(savedBoard);
    }
}
