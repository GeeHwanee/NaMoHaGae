package kr.kro.namohagae.member.service;

import kr.kro.namohagae.global.dao.TownDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TownService {

    @Autowired
    private TownDao townDao;

    public List<Map> read(String gu){
        return townDao.findByGu(gu);
    }



}
