package com.dailyon.memeberservice.address.service;

import com.dailyon.memeberservice.address.api.response.AddressGetResponse;
import com.dailyon.memeberservice.address.entity.Address;
import com.dailyon.memeberservice.address.api.request.AddressCreateRequest;
import com.dailyon.memeberservice.address.repository.AddressRepository;
import com.dailyon.memeberservice.member.entity.Member;
import com.dailyon.memeberservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;



    @Transactional
    public Page<AddressGetResponse> getMemberAddress(Long memberId, Pageable pageable){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));

        Page<Address> addresses = addressRepository.findByMemberId(memberId, pageable);

        Page<AddressGetResponse> addressResponses = addresses.map(address -> new AddressGetResponse(
                address.getId(),
                address.getIsDefault(),
                address.getName(),
                address.getDetailAddress(),
                address.getRoadAddress(),
                address.getPostCode(),
                address.getPhoneNumber()
        ));

        return addressResponses;
    }



    @Transactional
    public Long createAddress(AddressCreateRequest request, Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));

        Address address = Address.builder()
                        .member(member)
                        .isDefault(request.getIsDefault())
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
