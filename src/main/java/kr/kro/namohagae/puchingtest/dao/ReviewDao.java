package kr.kro.namohagae.puchingtest.dao;

import kr.kro.namohagae.puchingtest.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewDao {

    public List<ReviewDto.profile> findContentByReceiverNo(Integer startRownum, Integer endRownum,Integer memberNo);
    public List<ReviewDto.Imformation>findContentByWriterNo(Integer startRownum, Integer endRownum,Integer memberNo);
    public Integer countReceiver(Integer memberNo);
    public Integer countWriter(Integer memberNo);

}
