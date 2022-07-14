package com.security.security.controller;

import com.security.security.model.Board;
import com.security.security.model.Comment;
import com.security.security.model.User;
import com.security.security.model.dto.request.BoardCreateRequestDto;
import com.security.security.model.dto.request.CommentCreateRequestDto;
import com.security.security.service.BoardService;
import com.security.security.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/board/{boardId}/comment")
    public ResponseEntity createComent(
            @RequestBody CommentCreateRequestDto commentCreateRequestDto,
            @PathVariable Long boardId,
            @AuthenticationPrincipal String username){

        Comment comment = commentService.createComment(commentCreateRequestDto, username, boardId);

        return new ResponseEntity(comment, HttpStatus.CREATED);
    }
}
