package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.dto.AddressDto;
import kr.kro.namohagae.mall.entity.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressDao {
    public Integer save(Address address);

    public List<AddressDto.Read> findAll(Integer memberNo);

    public Boolean delete(Integer addressNo);

    public Address findByMemberNoAndAddressNo(Integer memberNo, Integer AddressNo);
}
