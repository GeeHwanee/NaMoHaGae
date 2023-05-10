package kr.kro.namohagae.global.dao;

import kr.kro.namohagae.global.dto.NotificationDto;
import kr.kro.namohagae.global.entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationDao {

    public Integer save(Notification notification);

    @Select("select NOTIFICATION_SEQ_NOTIFICATION_NO.currval from dual")
    public Integer getSelectKey();

    public List<NotificationDto.FindAll> findAll(Integer startRownum, Integer endRownum, Integer memberNo);
    public Integer count(Integer memberNo);

    public Integer update(Integer notificationNo);

    public List<NotificationDto.FindAll> findByNotificationReadEnabled(Integer memberNo);
}
