package project.communityservice.domain.board.post.dto.response;

import project.communityservice.domain.board.post.entity.Board;
import project.communityservice.domain.board.tag.entity.BoardTag;
import java.util.List;
import java.util.stream.Collectors;

public record TagResponse(
        Long tagId,
        String tagName
) {
    public static TagResponse of(BoardTag tag) {
        return new TagResponse(
                tag.getId(),
                tag.getTag()
        );
    }

    public static List<TagResponse> listOf(List<BoardTag> tags) {
        return  tags.stream()
                .map(TagResponse::of)
                .collect(Collectors.toList());
    }
}

