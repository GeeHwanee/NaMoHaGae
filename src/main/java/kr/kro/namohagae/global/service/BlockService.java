package kr.kro.namohagae.global.service;

import kr.kro.namohagae.global.dao.BlockDao;
import kr.kro.namohagae.global.dao.ReportDao;
import kr.kro.namohagae.global.dto.BlockDto;
import kr.kro.namohagae.global.entity.Block;
import kr.kro.namohagae.member.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlockService {
    @Autowired
    private BlockDao blockDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private ReportDao reportDao;

    public Boolean save(BlockDto.save dto) {
        String dateString = dto.getBlockDeadlineDate();
        String pattern = "yyyy-MM-dd";
        LocalDate dateTime = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(pattern));
        Block block = dto.toEntity(dateTime);
        System.out.println(dto.getMemberNo());
        Boolean check = blockDao.checkByMemberNo(dto.getMemberNo());
        System.out.println(check);
        if (check == true){
            return false;
        }
        memberDao.memberEnabled(dto.getMemberNo(),false);
        return blockDao.save(block);
    }
    public Boolean delete(LocalDate today){
        List<Integer> leavePrisons = blockDao.findMemberNoByToday(today);
        System.out.println(today);
        List<Integer> reportNo = new ArrayList<>();
        Integer result =0;
        for (Integer i: leavePrisons) {
            System.out.println(i);
            result = blockDao.findReportByMemberNo(i);
            reportNo.add(result);
            System.out.println(result);
            memberDao.memberEnabled(i,true);
        }
        Boolean a = blockDao.delete(today);
        if (reportNo!=null){
            for (Integer i:reportNo) {
                reportDao.delete(i);
            }
        }
        return a;
    }


}
