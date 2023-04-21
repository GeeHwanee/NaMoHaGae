package kr.kro.namohagae.puchingtest.service;

import kr.kro.namohagae.puchingtest.dao.Puchingdao;
import kr.kro.namohagae.puchingtest.dto.PuchingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PuchingService {
    @Autowired
    private Puchingdao dao;

    public List<PuchingDto.readTown> findAllTown() {
        System.out.println("퍼칭서비스-puchingmap실행");
        List<PuchingDto.readTown> list = dao.findAllTown();
        System.out.println("퍼칭서비스-puchingmap완료");
        return list;
    }

    public List<PuchingDto.readUser> readUsers(Double latitude,Double longitude,Integer pageNum,Integer pageSize){
        Integer startrownum=1+((pageNum-1)*10);
        Integer endrownum=pageSize*pageNum;


        List<PuchingDto.readUser> list =dao.findByUsers(latitude,longitude,startrownum,endrownum);

        return  list;
    }
}

