package project.communityservice.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.communityservice.domain.board.dto.BoardDTO;
import project.communityservice.domain.board.dto.request.BoardCreateRequest;
import project.communityservice.domain.board.dto.request.BoardUpdateRequest;
import project.communityservice.domain.board.dto.response.BoardCreateResponse;
import project.communityservice.domain.board.dto.response.BoardResponse;
import project.communityservice.domain.board.dto.response.BoardUpdateResponse;
import project.communityservice.domain.board.dto.response.BoardsResponse;
import project.communityservice.domain.board.entity.Board;
import project.communityservice.domain.board.repository.BoardRepository;
import project.communityservice.domain.board.service.BoardService;
import project.communityservice.global.jpa.RsData;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class ApiV1BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    // 게시글 목록
    @GetMapping
    public RsData<BoardsResponse> boardList(){
        List<BoardDTO> boardList = boardService.getBoards();

        return RsData.of("200", "게시글 목록 조회", new BoardsResponse(boardList));
    }

    // 게시물 단건 조회
    @GetMapping("/{id}")
    public RsData<BoardResponse> board(@PathVariable("id") Long id){
        BoardDTO boardDTO = boardService.getBoard(id);

        return RsData.of("200", "게시글 단건 조회", new BoardResponse(boardDTO));
    }

    // 게시물 등록
    @PostMapping
    public RsData<BoardCreateResponse> createBoard(@RequestBody BoardCreateRequest boardCreateRequest){
        BoardDTO boardDTO = boardService.createBoard(boardCreateRequest.getTitle(), boardCreateRequest.getContent());

        return RsData.of("200", "게시글 등록", new BoardCreateResponse(boardDTO));
    }

    // 게시물 수정
    // Put 전체 , Patch 일정 부분
    @PatchMapping("/{id}")
    public RsData<BoardUpdateResponse> updateBoard(@RequestBody BoardUpdateRequest boardUpdateRequest, @PathVariable("id") Long id){
        Board board = boardRepository.getByIdOrThrow(id);

        BoardDTO boardDTO = boardService.updateBoard(board, boardUpdateRequest.getTitle(), boardUpdateRequest.getContent());

        return RsData.of("200", "게시글 수정", new BoardUpdateResponse(boardDTO));
    }

    // 게시물 삭제
    @DeleteMapping("/{id}")
    public RsData<BoardResponse> deleteBoard(@PathVariable("id") Long id){

        Board board = boardRepository.getByIdOrThrow(id);

        boardService.deleteBoard(id);

        BoardDTO boardDTO = new BoardDTO(board);

        return RsData.of("200", "게시글 삭제", new BoardResponse(boardDTO));
    }
}
