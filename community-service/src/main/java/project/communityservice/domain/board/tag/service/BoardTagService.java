package project.communityservice.domain.board.tag.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.communityservice.domain.board.post.dto.response.BoardResponse;
import project.communityservice.domain.board.post.entity.Board;
import project.communityservice.domain.board.tag.dto.response.TagResponse;

import java.util.List;

public interface BoardTagService {
    // 해당 태그만 나오게 하는 검색 기능
    // 페이징 처리
    List<TagResponse> tagSerach(String tagName, Pageable pageable);
}
