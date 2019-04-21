package ojt.crscube.board.domain.repository;

import ojt.crscube.board.domain.model.Board;
import ojt.crscube.board.infra.BoardCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by taesu on 2019-04-21.
 */
public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {
}
