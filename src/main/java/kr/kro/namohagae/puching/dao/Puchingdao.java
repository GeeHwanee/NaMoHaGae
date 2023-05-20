package kr.kro.namohagae.puching.dao;

import kr.kro.namohagae.puching.dto.PuchingDto;
import kr.kro.namohagae.puching.entity.Puching;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Puchingdao {


    @Select("select t.town_no as townNo,t.town_dong as townDong,t.town_latitude as townLatitude," +
            "t.town_longitude as townLongitude,count(m.town_no) as townCnt " +
            "from TOWN t,MEMBER m   where t.TOWN_NO=m.TOWN_NO(+) " +
            "group by t.town_no,t.town_dong,t.town_latitude,t.town_longitude,m.town_no order by townNO")
    public List<PuchingDto.readTown> findAllTown();
    public List<PuchingDto.readUser> findByUsers(String url, Double latitude,Double longitude,Integer startrownum,Integer endrownum,Integer memberNo);


    public Integer checkPuching(Integer senderNo,Integer receiverNo);  // 체크 잘되는지 테스트 필요

    public void savePuching(Puching puching);

    public void updatePuchingStatus(Integer messageNo,String puchingStatus);

    public Integer checkWritePuchingReviewBysenderNo(Integer senderNo,Integer messageNo);

    public String checkPuchingStatus(Integer messageNo);

    public Integer findPuchingNoBySenderNoAndReceiverNo(Integer senderNo,Integer receiverNo);
}
