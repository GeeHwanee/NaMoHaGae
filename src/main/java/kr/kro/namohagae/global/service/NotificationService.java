package kr.kro.namohagae.global.service;

import kr.kro.namohagae.global.dao.NotificationDao;
import kr.kro.namohagae.global.dto.NotificationDto;
import kr.kro.namohagae.global.entity.Notification;
import kr.kro.namohagae.global.util.pagination.Pagination;
import kr.kro.namohagae.global.websocket.WebSocketService;
import kr.kro.namohagae.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class NotificationService {
    private final NotificationDao notificationDao;
    private final WebSocketService webSocketService;
    private final static Integer PAGESIZE=10;
    private final static Integer BLOCKSIZE=5;

    public void save(Member member, String notificationContent, String notificationLink){
        Notification notification = Notification.builder().memberNo(member.getMemberNo()).notificationContent(notificationContent).notificationLink(notificationLink).build();
        notificationDao.save(notification);
        NotificationDto.FindAll notificationRead = new NotificationDto.FindAll(notification.getNotificationNo(),notification.getNotificationContent(),notification.getNotificationLink(),false);
        webSocketService.sendMessage(member.getMemberEmail(), notificationRead);
    }


    public NotificationDto.Pagination findAll(Integer pageno, Integer memberNo) {
        Integer countOfNotification = notificationDao.count(memberNo);
        Pagination page = new Pagination(BLOCKSIZE, PAGESIZE, pageno, countOfNotification);
        List<NotificationDto.FindAll> notifications = notificationDao.findAll(page.getStartRowNum(), page.getEndRowNum(),memberNo);
        return new NotificationDto.Pagination(pageno, page.getPrevPage(), page.getStartPage(), page.getEndPage(), page.getNextPage(), notifications);
    }

    public void read (Integer notificationNo){
        notificationDao.update(notificationNo);
    }

    public List<NotificationDto.FindAll> printNotificationList(Integer memberNo){
        return notificationDao.findByNotificationReadEnabled(memberNo);
    }


}
