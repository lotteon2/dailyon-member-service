package com.dailyon.memeberservice.member.repository;

import com.dailyon.memeberservice.member.entity.Member;
import com.dailyon.memeberservice.member.repository.custom.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

}
