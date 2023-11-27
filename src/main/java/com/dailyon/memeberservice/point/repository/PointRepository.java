package com.dailyon.memeberservice.point.repository;

import com.dailyon.memeberservice.member.entity.Member;
import com.dailyon.memeberservice.member.repository.custom.MemberRepositoryCustom;
import com.dailyon.memeberservice.point.entity.PointHistory;
import com.dailyon.memeberservice.point.repository.custom.PointHistoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<PointHistory, Long>, PointHistoryRepositoryCustom {
}
