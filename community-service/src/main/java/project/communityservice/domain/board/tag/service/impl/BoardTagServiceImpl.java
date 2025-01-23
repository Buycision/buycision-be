package project.communityservice.domain.board.tag.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.communityservice.domain.board.post.dto.response.BoardResponse;
import project.communityservice.domain.board.post.entity.Board;
import project.communityservice.domain.board.tag.dto.response.TagResponse;
import project.communityservice.domain.board.tag.entity.BoardTag;
import project.communityservice.domain.board.tag.repository.BoardTagRepository;
import project.communityservice.domain.board.tag.service.BoardTagService;

import javax.swing.text.html.HTML;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardTagServiceImpl implements BoardTagService {
    private final BoardTagRepository boardTagRepository;

    @Override
    public List<TagResponse> tagSerach(String tagName, Pageable pageable) {
        Page<BoardTag> boards = boardTagRepository.findAllByTag(tagName, pageable);
        List<BoardTag> boardTags = boards.getContent();

        return TagResponse.listOf(boardTags);

    }
}
