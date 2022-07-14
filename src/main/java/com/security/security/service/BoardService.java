package com.security.security.service;

import com.security.security.model.Board;
import com.security.security.model.User;
import com.security.security.model.dto.request.BoardCreateRequestDto;
import com.security.security.model.dto.request.BoardUpdateRequestDto;
import com.security.security.repository.BoardRepository;
import com.security.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    @Transactional
    public Board updateBoard(BoardUpdateRequestDto dto, String username, Long boardId){
        User user = userRepository.findByUsername(username).get();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글이 없습니다."));
        board.setContext(dto.getContext());
        return boardRepository.save(board);
    }

    public List<Board> getAllBoard(){
        return boardRepository.findAll();
    }

    @Transactional
    public void delete(String username, Long boardId){

        User user = userRepository.findByUsername(username).get();

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글이 없습니다."));

        if(!board.getId().equals(user.getId()))
            throw new RuntimeException("작성자가 아닙니다.");

        boardRepository.deleteById(boardId);
    }

}
