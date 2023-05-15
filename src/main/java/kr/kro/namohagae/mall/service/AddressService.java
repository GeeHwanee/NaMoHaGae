package kr.kro.namohagae.mall.service;

import kr.kro.namohagae.mall.dao.AddressDao;
import kr.kro.namohagae.mall.dto.AddressDto;
import kr.kro.namohagae.mall.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressDao dao;
    public void save(Integer memberNo,AddressDto.save dto){
        String name = "";
        if (dto.getAddressName()!=null){
            name=dto.getAddressName();
        }
        Address address = dto.toEntity(memberNo,name);
        System.out.println(address.getAddressAddress());
        System.out.println(address.getAddressAddressDetail());
        System.out.println(address.getAddressName());
        System.out.println(address.getMemberNo());
        System.out.println(address.getAddressPostcode());
        dao.save(address);
    }

    public List<AddressDto.Read> printAddressAll(Integer memberNo){
        return dao.findAll(memberNo);
    }

    public Boolean delete(Integer addressNo){
        return dao.delete(addressNo);
    }

    public List<Address> setDefault(Integer addressNo, Integer memberNo) {
        dao.resetDefault(memberNo);
        dao.setDefault(addressNo);
        return dao.findByMemberNo(memberNo);
    }
}
