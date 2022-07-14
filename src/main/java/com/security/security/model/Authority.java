package com.security.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="authority_id")
    @JsonIgnore
    private Long id;
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String authority;

    public void setUser(User user) {
        this.user = user;
        //무한루프에 빠지지 않도록 체크
        if (!user.getAuthorities().contains(this)) {
            user.getAuthorities().add(this);
            System.out.println("test3");
            System.out.println(user.getAuthorities());
        }
    }
}
