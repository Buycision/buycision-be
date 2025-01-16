package project.communityservice.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.communityservice.domain.board.entity.Board;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BoardDTO {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    public BoardDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdDate = board.getCreateDate();
        this.modifiedDate = board.getModifyDate();

    }

    public Board toEntity() {
        return Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .createDate(createdDate)
                .modifyDate(modifiedDate)
                .build();
    }
}
