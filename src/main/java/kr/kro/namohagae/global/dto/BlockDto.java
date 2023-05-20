package kr.kro.namohagae.global.dto;

import kr.kro.namohagae.global.entity.Block;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BlockDto {
    @Data
    public static class save {
        private Integer memberNo;
        private Integer reportNo;
        private String blockDeadlineDate;

        public Block toEntity(LocalDate blockDeadlineDate){
          return   Block.builder().memberNo(memberNo).reportNo(reportNo).blockDeadlineDate(blockDeadlineDate).build();
        }
    }
}
