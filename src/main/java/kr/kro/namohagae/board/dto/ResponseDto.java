package kr.kro.namohagae.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
public class ResponseDto {
    private PageDto pageDto;
    private List<BoardTownListDto> boardTownListDto;


}
