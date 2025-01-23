package project.communityservice.domain.board.tag.dto.response;

import org.springframework.data.domain.Page;
import project.communityservice.domain.board.post.entity.Board;
import project.communityservice.domain.board.tag.entity.BoardTag;
import java.util.List;
import java.util.stream.Collectors;

public record TagResponse(
       Long tagId,
       String tagName,
       List<Board> boards // 게시글
) {
    public static TagResponse of(BoardTag tag) {
        return new TagResponse(
                tag.getId(),
                tag.getTag(),
                tag.getBoard()
        );
    }

    public static List<TagResponse> listOf(List<BoardTag> tags) {
        return  tags.stream()
                .map(TagResponse::of)
                .collect(Collectors.toList());
    }
}
