package com.security.security.controller;

import com.security.security.model.Board;
import com.security.security.model.Comment;
import com.security.security.model.dto.request.BoardCreateRequestDto;
import com.security.security.model.dto.request.BoardUpdateRequestDto;
import com.security.security.model.dto.request.CommentCreateRequestDto;
import com.security.security.model.dto.request.CommentUpdateRequestDto;
import com.security.security.service.BoardService;
import com.security.security.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private CommentService commentService;

    @PostMapping("/board")
    public ResponseEntity createBoard(
            @RequestBody BoardCreateRequestDto boardCreateRequestDto,
            @AuthenticationPrincipal String username){

        Board board = boardService.createBoard(boardCreateRequestDto, username);

        return new ResponseEntity(board, HttpStatus.CREATED);
    }

    @GetMapping("/board")
    public ResponseEntity getAllBoard(){

        List<Board> boards = boardService.getAllBoard();

        return new ResponseEntity(boards, HttpStatus.CREATED);
    }

    @PutMapping("/board/{boardId}")
    public ResponseEntity putBoard(
            @PathVariable Long boardId,
            @AuthenticationPrincipal String username,
            @RequestBody BoardUpdateRequestDto boardUpdateRequestDto){

        Board board = boardService.updateBoard(boardUpdateRequestDto,username, boardId);

        return new ResponseEntity(board, HttpStatus.OK);
    }

    @DeleteMapping("/board/{boardId}")
    public ResponseEntity deleteBoard(
            @PathVariable Long boardId,
            @AuthenticationPrincipal String username){

        boardService.delete(username, boardId);

        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping("/board/{boardId}/comment")
    public ResponseEntity createComment(
            @RequestBody CommentCreateRequestDto commentCreateRequestDto,
            @PathVariable Long boardId,
            @AuthenticationPrincipal String username){

        Comment comment = commentService.createComment(commentCreateRequestDto, username, boardId);

        return new ResponseEntity(comment, HttpStatus.CREATED);
    }

    @GetMapping("/board/{boardId}/comment")
    public ResponseEntity getAllComment(
            @PathVariable Long boardId){

        List<Comment> comments = commentService.getAllComment(boardId);

        return new ResponseEntity(comments, HttpStatus.CREATED);
    }

    @PutMapping("/board/{boardId}/comment/{commentId}")
    public ResponseEntity putComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal String username,
            @RequestBody CommentUpdateRequestDto commentUpdateRequestDto){

        Comment comment = commentService.updateComment(commentUpdateRequestDto,username, commentId);

        return new ResponseEntity(comment, HttpStatus.OK);
    }

    @DeleteMapping("/board/{boardId}/comment/{commentId}")
    public ResponseEntity deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal String username){

        commentService.delete(username, commentId);

        return new ResponseEntity(HttpStatus.OK);
    }
}
