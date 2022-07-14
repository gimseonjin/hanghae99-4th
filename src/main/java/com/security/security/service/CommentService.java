package com.security.security.service;

import com.security.security.model.Board;
import com.security.security.model.Comment;
import com.security.security.model.User;
import com.security.security.model.dto.request.CommentCreateRequestDto;
import com.security.security.repository.BoardRepository;
import com.security.security.repository.CommentRepository;
import com.security.security.repository.UserRepository;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
