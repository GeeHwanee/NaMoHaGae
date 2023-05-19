package kr.kro.namohagae.global.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Block {
    private Integer blcokNo;
    private Integer memberNo;
    private Integer reportNo;
    private LocalDateTime blockDate;
    private LocalDateTime blockDeadLineDate;
}
