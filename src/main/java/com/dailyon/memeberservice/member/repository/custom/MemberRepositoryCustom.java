package com.dailyon.memeberservice.member.repository.custom;

import com.dailyon.memeberservice.member.entity.Member;

public interface MemberRepositoryCustom {
    boolean findByEmail(String email);

    Long findPointsById(Long memberId);

    Member findByCode(String referralCode);
}
