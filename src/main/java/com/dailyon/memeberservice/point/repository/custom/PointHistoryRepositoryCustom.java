package com.dailyon.memeberservice.point.repository.custom;

import com.dailyon.memeberservice.point.api.response.GetPointHistory;
import com.dailyon.memeberservice.point.entity.PointHistory;

import java.util.List;

public interface PointHistoryRepositoryCustom {
    List<GetPointHistory> findByMemberId(Long memberId);


}
