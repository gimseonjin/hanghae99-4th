package com.security.security.service;

import com.security.security.model.Board;
import com.security.security.model.User;
import com.security.security.model.dto.request.BoardCreateRequestDto;
import com.security.security.repository.BoardRepository;
import com.security.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Board createBoard(BoardCreateRequestDto dto, String username){
        User user = userRepository.findByUsername(username).get();
        Board board = dto.toBoard();
        board.setUser(user);
        return boardRepository.save(board);
    }
}
