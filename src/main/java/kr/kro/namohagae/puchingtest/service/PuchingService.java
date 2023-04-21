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

    public List<PuchingDto.readTown> readTown() {
        System.out.println("퍼칭서비스-puchingmap실행");
        List<PuchingDto.readTown> list = dao.findAllTown();
        System.out.println("퍼칭서비스-puchingmap완료");
        return list;
    }

    public List<PuchingDto.readUser> readUsers(){
        return null;
    }
}

