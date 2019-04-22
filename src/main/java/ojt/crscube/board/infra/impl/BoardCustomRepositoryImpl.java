package ojt.crscube.board.infra.impl;

import com.querydsl.jpa.JPQLQuery;
import ojt.crscube.base.interfaces.criteria.SearchCriteria;
import ojt.crscube.board.domain.model.Board;
import ojt.crscube.board.domain.model.QBoard;
import ojt.crscube.board.infra.BoardCustomRepository;
import ojt.crscube.member.domain.model.QMember;
import ojt.crscube.member.domain.model.QMemberPassword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import static ojt.crscube.board.infra.predicates.BoardPredicatesBuilder.buildOrder;
import static ojt.crscube.board.infra.predicates.BoardPredicatesBuilder.buildPredicate;

/**
 * Created by taesu on 2019-04-21.
 */
public class BoardCustomRepositoryImpl extends QuerydslRepositorySupport implements BoardCustomRepository {
    public BoardCustomRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> findAllWithMember(SearchCriteria searchCriteria, Pageable pageable) {
        final String memberAlias = "m";
        final String boardAlias = "b";

        //BoardPredicateBuilder에서 지정한 PathBuilder에 variable과 동일하게 줄 것
        QMember qMember = new QMember(memberAlias);
        QBoard qBoard = new QBoard(boardAlias);
        QMemberPassword memberPassword = new QMemberPassword("qp");

        JPQLQuery<Board> query
                = from(qBoard).innerJoin(qBoard.writer, qMember).fetchJoin()
                              .where(buildPredicate(searchCriteria, memberAlias, boardAlias));

        query.offset(pageable.getOffset());
        query.limit(pageable.getPageSize());
        query.orderBy(buildOrder(pageable, memberAlias, boardAlias));

        return new PageImpl<>(query.fetch(), pageable, query.fetchCount());
    }
}
