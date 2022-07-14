package com.security.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    @JsonIgnore
    private String password;
    @JsonIgnore
    private boolean enabled;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<Authority> authorities;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Board> boards;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Comment> comments;

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    public void addBoard(Board board) {
        this.boards.add(board);
        //무한루프에 빠지지 않도록 체크
        if (board.getUser() != this) {
            board.setUser(this);
        }
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        //무한루프에 빠지지 않도록 체크
        if (comment.getUser() != this) {
            comment.setUser(this);
        }
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
        //무한루프에 빠지지 않도록 체크
        if (authority.getUser() != this) {
            authority.setUser(this);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
