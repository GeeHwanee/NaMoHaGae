package kr.kro.namohagae.global.dao;

import kr.kro.namohagae.global.dto.ReportDto;
import kr.kro.namohagae.global.entity.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportDao {
    public Boolean save(Report report);

    public Boolean delete(Integer reportNo);

    public List<ReportDto.FindAll> findAll(Integer startRownum,Integer endRownum);

    public List<ReportDto.FindAll> findAllByMemberNo(Integer memberNo,Integer startRownum,Integer endRownum);

    public  Integer countReportByMemberNo(Integer memberNo);

    public Integer countAll();

    public Integer findMemberNoByNickname(String nickname);
}
