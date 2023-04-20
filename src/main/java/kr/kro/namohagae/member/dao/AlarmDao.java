package kr.kro.namohagae.member.dao;

import kr.kro.namohagae.member.dto.AlarmDto;
import kr.kro.namohagae.member.entity.Alarm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlarmDao {

    public void save(Alarm alarm);

    public List<AlarmDto.FindAll> findAll(Integer startRownum, Integer endRownum);
    public Integer count();

    public AlarmDto.FindAll read(Integer no);
    public Integer alarmRead(Integer no);

}
