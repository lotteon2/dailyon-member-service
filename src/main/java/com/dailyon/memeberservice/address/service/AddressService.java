package com.dailyon.memeberservice.address.service;

import com.dailyon.memeberservice.address.entity.Address;
import com.dailyon.memeberservice.address.entity.AddressCreateRequest;
import com.dailyon.memeberservice.address.repository.AddressRepository;
import com.dailyon.memeberservice.member.api.request.MemberCreateRequest;
import com.dailyon.memeberservice.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public Long createAddress(AddressCreateRequest request, Long memberId){

        Address address = Address.builder()
                        .member_id(memberId)
                        .is_default(request.is_default())
                        .name((request.getName()))
                        .detail_address(request.getDetail_address())
                        .road_address(request.getRoad_address())
                        .post_code(request.getPost_code())
                        .phone_number(request.getPhone_number())
                        .build();

        addressRepository.save(address);

        return address.getId();
    }
}
