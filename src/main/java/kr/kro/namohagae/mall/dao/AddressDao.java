package kr.kro.namohagae.mall.dao;

import kr.kro.namohagae.mall.dto.AddressDto;
import kr.kro.namohagae.mall.entity.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressDao {
    public Boolean save(Address address);

    public List<AddressDto.Read> findAll(Integer memberNo);

    public Boolean delete(Integer addressNo);

    public Address findByMemberNoAndAddressNo(Integer memberNo, Integer addressNo);

    public List<Address> findByMemberNo(Integer memberNo);

    public Integer resetDefault(Integer memberNo);

    public Integer setDefault(Integer addressNo);

}
