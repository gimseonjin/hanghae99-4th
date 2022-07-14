package com.security.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String context;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "board")
    private List<Comment> comments;


    public void setUser(User user) {
        this.user = user;
        //무한루프에 빠지지 않도록 체크
        if (!user.getBoards().contains(this)) {
            user.getBoards().add(this);
        }
    }

    public void addComments(Comment comment) {
        this.comments.add(comment);
        //무한루프에 빠지지 않도록 체크
        if (comment.getBoard() != this) {
            comment.setBoard(this);
        }
    }
}
