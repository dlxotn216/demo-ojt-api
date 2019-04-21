package ojt.crscube.board.application;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.interfaces.criteria.SearchCriteria;
import ojt.crscube.board.domain.repository.BoardRepository;
import ojt.crscube.board.interfaces.dto.BoardDto.BoardsSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Created by taesu on 2019-04-21.
 */
@Component @RequiredArgsConstructor
public class BoardSearchService {
    private final BoardRepository boardRepository;


    public Page<BoardsSearchResponse> searchBoards(String searchOptionString, String condition, Pageable pageable) {

        Page<BoardsSearchResponse> map = this.boardRepository.findAllWithMember(new SearchCriteria(searchOptionString, condition), pageable)
                .map(BoardsSearchResponse::from);

        Long totalCount = map.getTotalElements();
        for (BoardsSearchResponse response : map) {
            response.setNo(totalCount--);
        }
        return map;
    }
}
