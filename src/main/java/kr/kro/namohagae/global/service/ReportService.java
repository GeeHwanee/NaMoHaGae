package kr.kro.namohagae.global.service;

import kr.kro.namohagae.global.dao.ReportDao;
import kr.kro.namohagae.global.dto.NotificationDto;
import kr.kro.namohagae.global.dto.ReportDto;
import kr.kro.namohagae.global.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportDao reportDao;
    private final static Integer PAGESIZE=10;
    private final static Integer BLOCKSIZE=5;
    public ReportDto.Pagination findAll(Integer pageno, Integer memberNo) {
        Integer countOfProduct = reportDao.countReportByMemberNo(memberNo);
        Integer countOfPage = (countOfProduct-1)/PAGESIZE + 1;

        pageno = Math.abs(pageno);
        if(pageno>countOfPage)
            pageno = countOfPage;

        Integer startRownum = (pageno-1)*PAGESIZE + 1;
        Integer endRownum = startRownum + PAGESIZE - 1;
        List<ReportDto.FindAll> reports = reportDao.findAll(startRownum, endRownum);
        // 리스트 log로 찍어
        Integer prev = (pageno-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new ReportDto.Pagination(pageno, prev, start, end, next, reports);
    }
    public ReportDto.Pagination findAllByMemberNo(Integer pageno, Integer memberNo) {
        Integer countOfProduct = reportDao.countReportByMemberNo(memberNo);
        Integer countOfPage = (countOfProduct-1)/PAGESIZE + 1;

        pageno = Math.abs(pageno);
        if(pageno>countOfPage)
            pageno = countOfPage;

        Integer startRownum = (pageno-1)*PAGESIZE + 1;
        Integer endRownum = startRownum + PAGESIZE - 1;
        List<ReportDto.FindAll> reports = reportDao.findAllByMemberNo(startRownum, endRownum,memberNo);
        // 리스트 log로 찍어
        Integer prev = (pageno-1)/BLOCKSIZE * BLOCKSIZE;
        Integer start = prev+1;
        Integer end = prev + BLOCKSIZE;
        Integer next = end+1;
        if(end>=countOfPage) {
            end = countOfPage;
            next = 0;
        }
        return new ReportDto.Pagination(pageno, prev, start, end, next, reports);
    }
    public Boolean save(ReportDto.Save save, Integer reportMemberNo){
        String reportContent = "";
        System.out.println(reportMemberNo);
        if(save.getReportContent()!=null){
            reportContent=save.getReportContent();
        }
        Report report = save.toEntity(reportContent,reportMemberNo);
        System.out.println(report.getMemberNo());
        System.out.println(report.getReportCategory());
        System.out.println(report.getReportDate());
        System.out.println(report.getReportContent());
        System.out.println(report.getReportMemberNo());
        return reportDao.save(report);
    }

    public Boolean delete(Integer reportNo){
        return reportDao.delete(reportNo);
    }
}
