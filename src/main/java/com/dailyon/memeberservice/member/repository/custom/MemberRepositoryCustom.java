package com.dailyon.memeberservice.member.repository.custom;

public interface MemberRepositoryCustom {
    boolean findByEmail(String email);

    Long findPointsById(Long memberId);
}
