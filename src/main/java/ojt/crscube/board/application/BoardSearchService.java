package ojt.crscube.board.application;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.interfaces.criteria.SearchCriteria;
import ojt.crscube.board.domain.model.Board;
import ojt.crscube.board.domain.repository.BoardRepository;
import ojt.crscube.board.interfaces.dto.BoardDto.BoardsSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static ojt.crscube.base.utils.Messages.ENTITY_NOT_FOUND;

/**
 * Created by taesu on 2019-04-21.
 */
@Component @RequiredArgsConstructor
public class BoardSearchService {
    private final BoardRepository boardRepository;
    
    public Board searchOrThrow(Long boardKey){
        return this.boardRepository.findById(boardKey)
                                   .orElseThrow(() -> new IllegalArgumentException(ENTITY_NOT_FOUND));
    }


    public Page<BoardsSearchResponse> searchBoards(String searchOptionString, String condition, Pageable pageable) {        
        Page<BoardsSearchResponse> responsePage
                = this.boardRepository.findAllWithMember(new SearchCriteria(searchOptionString, condition), pageable)
                                      .map(BoardsSearchResponse::from);

        Long totalCount = responsePage.getTotalElements();
        for (BoardsSearchResponse response : responsePage) {
            response.setNo(totalCount--);
        }
        
        return responsePage;
    }
}
