package project.communityservice.domain.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.communityservice.domain.board.dto.request.BoardCreateRequest;
import project.communityservice.domain.board.dto.request.BoardUpdateRequest;
import project.communityservice.domain.board.dto.response.BoardResponse;
import project.communityservice.domain.board.entity.Board;
import project.communityservice.domain.board.repository.BoardRepository;
import project.communityservice.domain.board.service.BoardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class ApiV1BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    // 게시글 목록
    @GetMapping
    public ResponseEntity<List<BoardResponse>> boardList(){
        return ResponseEntity.ok(boardService.getBoards());
    }

    // 게시물 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> board(@PathVariable("id") Long id){
        return ResponseEntity.ok(boardService.getBoard(id));
    }

    // 게시물 등록
    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@Valid @RequestBody BoardCreateRequest boardCreateRequest){
        return ResponseEntity.ok(boardService.createBoard(boardCreateRequest.title(), boardCreateRequest.content()));
    }

    // 게시물 수정
    // Put 전체 , Patch 일정 부분
    @PatchMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard( @Valid @PathVariable("id") Long id,
                                                      @RequestBody BoardUpdateRequest boardUpdateRequest){

        return ResponseEntity.ok(boardService.updateBoard(id, boardUpdateRequest.title(), boardUpdateRequest.content()));
    }

    // 게시물 삭제
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable("id") Long id){
        boardService.deleteBoard(id);
    }
}
