package project.communityservice.domain.board.post.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.communityservice.domain.board.post.entity.Board;
import project.communityservice.domain.board.post.entity.QBoard;

import java.util.List;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Board> findAllByTag(Long id, Pageable pageable) {
        QBoard board = QBoard.board;
        QBoardTag boardTag = QBoardTag.boardTag;

        // 기본 쿼리
        List<Board> boards = queryFactory
                .selectFrom(board)
                .join(board.boardTag, boardTag)
                .where(boardTag.id.eq(id))
                .offset(pageable.getOffset())  // 페이지 시작 위치
                .limit(pageable.getPageSize()) // 한 페이지에 표시할 개수
                .fetch();

        // 전체 개수 조회 (페이징 처리를 위해 필요)
        long total = queryFactory
                .select(board.count())
                .from(board)
                .join(board.boardTag, boardTag)
                .where(boardTag.id.eq(id))
                .fetchOne();

        return new PageImpl<>(boards, pageable, total);
    }
}
