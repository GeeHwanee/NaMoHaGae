package kr.kro.namohagae.global.service;

import kr.kro.namohagae.global.dao.NotificationDao;
import kr.kro.namohagae.global.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NotificationService {
    @Autowired
    private NotificationDao notificationDao;
    private final static Integer PAGESIZE=5;
    private final static Integer BLOCKSIZE=3;

    public NotificationDto.Pagination findAll(Integer pageno, Integer memberNo) {
        Integer countOfProduct = notificationDao.count(memberNo);
        Integer countOfPage = (countOfProduct-1)/PAGESIZE + 1;

        pageno = Math.abs(pageno);
        if(pageno>countOfPage)
            pageno = countOfPage;

        Integer startRownum = (pageno-1)*PAGESIZE + 1;
        Integer endRownum = startRownum + PAGESIZE - 1;
        List<NotificationDto.FindAll> notifications = notificationDao.findAll(startRownum, endRownum,memberNo);
        // 리스트 log로 찍어
        Integer prev = (pageno-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new NotificationDto.Pagination(pageno, prev, start, end, next, notifications);

    }

    public void read (Integer bno){
        notificationDao.notificationRead(bno);
    }


}
