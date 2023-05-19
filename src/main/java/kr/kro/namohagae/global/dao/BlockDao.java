package kr.kro.namohagae.global.dao;

import kr.kro.namohagae.global.entity.Block;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BlockDao {
    public Boolean save(Block block);
    public Boolean delete(LocalDateTime today);
    public Integer findReportByMemberNo(Integer memberNo);
    public Boolean checkByMemberNo(Integer memberNo);
    public List<Integer> findMemberNoByToday(LocalDateTime today);
    public LocalDateTime findDeadlineDateByMemberNo(Integer memberNo);
}
