package com.security.security.service;

import com.security.security.model.Board;
import com.security.security.model.Comment;
import com.security.security.model.User;
import com.security.security.model.dto.request.CommentCreateRequestDto;
import com.security.security.model.dto.request.CommentUpdateRequestDto;
import com.security.security.repository.BoardRepository;
import com.security.security.repository.CommentRepository;
import com.security.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Comment createComment(CommentCreateRequestDto dto, String username, Long boardId){
        User user = userRepository.findByUsername(username).get();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글이 없습니다."));

        Comment comment = dto.toComment();
        comment.setBoard(board);
        comment.setUser(user);

        return commentRepository.save(comment);
    }

    public List<Comment> getAllComment(Long boardId){
        return commentRepository.findAll().stream()
                .filter(comment -> comment.getBoard().getId().equals(boardId))
                .collect(Collectors.toList());
    }
    @Transactional
    public Comment updateComment(CommentUpdateRequestDto dto, String username, Long commentId){
        User user = userRepository.findByUsername(username).get();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("게시글이 없습니다."));

        if(!comment.getId().equals(user.getId()))
            throw new RuntimeException("작성자가 아닙니다.");

        comment.setContext(dto.getContext());

        return commentRepository.save(comment);
    }

    @Transactional
    public void delete(String username, Long commentId){
        User user = userRepository.findByUsername(username).get();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("게시글이 없습니다."));

        if(!comment.getId().equals(user.getId()))
            throw new RuntimeException("작성자가 아닙니다.");

        commentRepository.deleteById(commentId);
    }

}
