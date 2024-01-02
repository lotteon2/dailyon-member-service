package com.dailyon.memeberservice.address.service;

import com.dailyon.memeberservice.address.api.request.AddressUpdateRequest;
import com.dailyon.memeberservice.address.api.response.AddressGetResponse;
import com.dailyon.memeberservice.address.entity.Address;
import com.dailyon.memeberservice.address.api.request.AddressCreateRequest;
import com.dailyon.memeberservice.address.repository.AddressRepository;
import com.dailyon.memeberservice.member.entity.Member;
import com.dailyon.memeberservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;


    public Page<AddressGetResponse> getMemberAddress(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));

        Page<Address> addresses = addressRepository.findByMemberId(member.getId(), pageable);

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
    public Long createAddress(AddressCreateRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));

        if (request.getIsDefault()) {
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
            setDefaultAddress(memberId, address.getId());
        } else {
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
        }

        return memberId;
    }


    @Transactional
    public Long setDefaultAddress(Long memberId, Long addressId) {
        List<Address> addresses = addressRepository.findByMemberId(memberId);

        for (Address address : addresses) {
            address.setIsDefault(address.getId().equals(addressId));
        }

        return addressId;
    }

    public AddressGetResponse getDefaultAddress(Long memberId) {
        Address address = addressRepository.findFirstByMemberIdAndIsDefault(memberId, true);
        AddressGetResponse response = new AddressGetResponse();

        response.setIsDefault(address.getIsDefault());
        response.setDetailAddress(address.getDetailAddress());
        response.setRoadAddress(address.getRoadAddress());
        ;
        response.setId(address.getId());
        response.setPhoneNumber(address.getPhoneNumber());
        response.setPostCode(address.getPostCode());
        response.setName(address.getName());

        return response;
    }

    @Transactional
    public Long deleteAddress(Long memberId, Long addressId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Address addressToDelete = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!addressToDelete.getMember().equals(member)) {
            throw new RuntimeException("Address does not belong to the member");
        }
        //addressRepository.delete(addressToDelete);
        addressRepository.deleteById(addressToDelete.getId());
        return addressId;
    }

    @Transactional
    public Long updateAddress(Long memberId, AddressUpdateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));


        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getMember().equals(member)) {
            throw new RuntimeException("Address does not belong to the member");
        }

        address.updateAddress(
                request.getName(),
                request.getDetailAddress(),
                request.getRoadAddress(),
                request.getPostCode(),
                request.getPhoneNumber()
        );


        return request.getAddressId();
    }

}
