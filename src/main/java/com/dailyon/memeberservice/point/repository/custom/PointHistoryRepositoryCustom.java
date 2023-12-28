package com.dailyon.memeberservice.point.repository.custom;

import com.dailyon.memeberservice.address.entity.Address;
import com.dailyon.memeberservice.point.api.response.GetPointHistory;
import com.dailyon.memeberservice.point.entity.PointHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PointHistoryRepositoryCustom {

    List<GetPointHistory> findByMemberId(Long memberId);

    Page<PointHistory> findByMemberId(Long memberId, Pageable pageable);


}
