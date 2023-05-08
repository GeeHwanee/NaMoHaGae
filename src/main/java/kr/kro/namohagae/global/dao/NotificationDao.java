package kr.kro.namohagae.global.dao;

import kr.kro.namohagae.global.dto.NotificationDto;
import kr.kro.namohagae.global.entity.Notification;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationDao {

    public void save(Notification notification);

    public List<NotificationDto.FindAll> findAll(Integer startRownum, Integer endRownum, Integer memberNo);
    public Integer count(Integer memberNo);

    public Integer notificationRead(Integer no);

    public List<NotificationDto.FindAll> quikMenu(Integer memberNo);
}
