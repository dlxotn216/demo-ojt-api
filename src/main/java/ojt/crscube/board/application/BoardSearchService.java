package ojt.crscube.board.application;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.domain.exception.EntityNotFoundException;
import ojt.crscube.base.interfaces.criteria.SearchCriteria;
import ojt.crscube.board.domain.model.Board;
import ojt.crscube.board.domain.repository.BoardRepository;
import ojt.crscube.board.interfaces.dto.BoardDto.BoardSearchResponse;
import ojt.crscube.board.interfaces.dto.BoardDto.BoardsSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static ojt.crscube.board.interfaces.dto.BoardDto.BoardSearchResponse.from;

/**
 * Created by taesu on 2019-04-21.
 */
@Component @RequiredArgsConstructor @Transactional(readOnly = true)
public class BoardSearchService {
    private final BoardRepository boardRepository;

    public BoardSearchResponse searchBoard(Long key) {
        return from(this.searchOrThrow(key));
    }

    public Board searchOrThrow(Long key) {
        return this.boardRepository.findById(key).orElseThrow(EntityNotFoundException::new);
    }

    public Page<BoardsSearchResponse> searchBoards(String searchOptionString, String condition, Pageable pageable) {
        Page<BoardsSearchResponse> responsePage
                = this.boardRepository.findAllWithMember(new SearchCriteria(searchOptionString, condition), pageable)
                                      .map(BoardsSearchResponse::from);

        Long startNo = responsePage.getTotalElements() - (pageable.getPageNumber() * pageable.getPageSize());
        for (BoardsSearchResponse response : responsePage) {
            response.setNo(startNo--);
        }

        return responsePage;
    }
}
