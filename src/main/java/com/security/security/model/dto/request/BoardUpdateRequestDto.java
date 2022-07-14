package com.security.security.model.dto.request;

import com.security.security.model.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardUpdateRequestDto {
    private String context;

    public Board toBoard(){
        return Board.builder()
                .context(context)
                .build();
    }
}
