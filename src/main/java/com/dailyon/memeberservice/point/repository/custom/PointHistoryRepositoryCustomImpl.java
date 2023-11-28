package com.dailyon.memeberservice.point.repository.custom;

import com.dailyon.memeberservice.point.api.response.GetPointHistory;
import com.dailyon.memeberservice.point.entity.PointHistory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class PointHistoryRepositoryCustomImpl implements PointHistoryRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<GetPointHistory> findByMemberId(Long memberId) {
        String jpql = "SELECT new com.dailyon.memeberservice.point.api.response.GetPointHistory(ph.amount, ph.status, ph.source, ph.utilize, ph.createdAt) FROM PointHistory ph WHERE ph.memberId = :memberId";
        TypedQuery<GetPointHistory> query = entityManager.createQuery(jpql, GetPointHistory.class);
        query.setParameter("memberId", memberId);
        return query.getResultList();
    }
}
