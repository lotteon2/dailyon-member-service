package com.dailyon.memeberservice.point.repository.custom;

import com.dailyon.memeberservice.point.entity.PointHistory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class PointHistoryRepositoryCustomImpl implements PointHistoryRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PointHistory> findByMemberId(Long memberId) {
        String jpql = "SELECT p FROM PointHistory p WHERE p.memberId = :memberId";
        TypedQuery<PointHistory> query = entityManager.createQuery(jpql, PointHistory.class);
        query.setParameter("memberId", memberId);

        List<PointHistory> pointHistories = query.getResultList();


        return pointHistories;
    }


}
