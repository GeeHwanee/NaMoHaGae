package kr.kro.namohagae.member.service;

import kr.kro.namohagae.member.dao.AlarmDao;
import kr.kro.namohagae.member.dto.AlarmDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AlarmService {
    @Autowired
    private AlarmDao alarmDao;
    private final static Integer PAGESIZE=5;
    private final static Integer BLOCKSIZE=3;
    public AlarmDto.Pagination findAll(Integer pageno,Integer memberNo) {
        Integer countOfProduct = alarmDao.count(memberNo);
        Integer countOfPage = (countOfProduct-1)/PAGESIZE + 1;

        pageno = Math.abs(pageno);
        if(pageno>countOfPage)
            pageno = countOfPage;

        Integer startRownum = (pageno-1)*PAGESIZE + 1;
        Integer endRownum = startRownum + PAGESIZE - 1;
        List<AlarmDto.FindAll> alarm = alarmDao.findAll(startRownum, endRownum,0);
        // 리스트 log로 찍어
        Integer prev = (pageno-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new AlarmDto.Pagination(pageno, prev, start, end, next, alarm);

    }

    public void read (Integer bno){
        alarmDao.alarmRead(bno);
    }


}
