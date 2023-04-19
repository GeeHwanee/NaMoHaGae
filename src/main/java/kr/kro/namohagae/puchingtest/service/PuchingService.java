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

    public void town() {

        System.out.println("111111111111111111111111111111111111111111111");
        List<PuchingDto.read> list = dao.findAllTown();
        for (PuchingDto.read town : list) {
            System.out.println(town.getTownNo());
            System.out.println(town.getTownDong());
            System.out.println(town.getTownLatitude());
            System.out.println(town.getTownLongitude());
            System.out.println("222222222222222222222222222222222222222222");
        }
    }
}
