package ojt.crscube.board.infra;

import ojt.crscube.base.interfaces.criteria.SearchCriteria;
import ojt.crscube.board.domain.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by taesu on 2019-04-21.
 */
public interface BoardCustomRepository {
    Page<Board> findAllWithMember(SearchCriteria searchCriteria, Pageable pageable);
}
