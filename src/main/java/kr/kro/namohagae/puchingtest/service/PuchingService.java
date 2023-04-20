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

    public List<PuchingDto.readTown> puchingmap() {

        System.out.println("퍼칭서비스-puchingmap실행");
        List<PuchingDto.readTown> list = dao.findAllTown();
        for (PuchingDto.readTown town : list) {
            System.out.println(town.getTownNo());
            System.out.println(town.getTownDong());
            System.out.println(town.getTownLatitude());
            System.out.println(town.getTownLongitude());
            System.out.println(town.getTownCnt());
        }
        System.out.println("퍼칭서비스-puchingmap완료");
        return list;
    }
}

