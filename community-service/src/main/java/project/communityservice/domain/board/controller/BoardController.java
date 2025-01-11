package project.communityservice.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.communityservice.domain.board.entity.Board;
import project.communityservice.domain.board.service.BoardService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    // 게시글 목록
    @GetMapping
    public String boardList(){
        return "게시물 목록입니다.";
    }

    // 게시물 등록
    @PostMapping
    public String addBoard(@RequestBody Board board){
        return "게시물 등록입니다.";
    }

    // 게시물 단건 조회
    @GetMapping("/{boardId}")
    public String boardDetail(@PathVariable long boardId){
        return "게시물 단건 조회입니다.";
    }

    // 게시물 수정
    // Put 전체 , Patch 일정 부분
    @PatchMapping
    public String updateBoard(@RequestBody Board board){
        return "게시물 일부 수정입니다.";
    }

    // 게시물 삭제
    @DeleteMapping
    public String deleteBoard(@PathVariable long boardId){
        return "게시물 삭제입니다.";
    }
}
