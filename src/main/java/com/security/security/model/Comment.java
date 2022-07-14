package com.security.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String context;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public void setUser(User user) {
        this.user = user;
        //무한루프에 빠지지 않도록 체크
        if (!user.getComments().contains(this)) {
            user.getComments().add(this);
        }
    }

    public void setBoard(Board board) {
        this.board = board;
        //무한루프에 빠지지 않도록 체크
        if (!board.getComments().contains(this)) {
            board.getComments().add(this);
        }
    }
}
