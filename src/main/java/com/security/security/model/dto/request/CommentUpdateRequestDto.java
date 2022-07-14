package com.security.security.model.dto.request;

import com.security.security.model.Board;
import com.security.security.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentUpdateRequestDto {
    private String context;

    public Comment toComment(){
        return Comment.builder()
                .context(context)
                .build();
    }
}
