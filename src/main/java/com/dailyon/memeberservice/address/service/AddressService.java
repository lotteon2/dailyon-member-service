package com.dailyon.memeberservice.address.service;

import com.dailyon.memeberservice.address.entity.Address;
import com.dailyon.memeberservice.address.api.request.AddressCreateRequest;
import com.dailyon.memeberservice.address.repository.AddressRepository;
import com.dailyon.memeberservice.member.entity.Member;
import com.dailyon.memeberservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createAddress(AddressCreateRequest request, Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));

        Address address = Address.builder()
                        .member(member)
                        .isDefault(request.isDefault())
                        .name((request.getName()))
                        .detailAddress(request.getDetailAddress())
                        .roadAddress(request.getRoadAddress())
                        .postCode(request.getPostCode())
                        .phoneNumber(request.getPhoneNumber())
                        .build();

        addressRepository.save(address);

        return address.getId();
    }
}
