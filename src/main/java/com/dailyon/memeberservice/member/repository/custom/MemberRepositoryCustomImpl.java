package com.dailyon.memeberservice.member.repository.custom;

import com.dailyon.memeberservice.member.entity.Member;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public boolean findByEmail(String email) {
        Long count = entityManager.createQuery("SELECT COUNT(m) FROM Member m WHERE m.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();

        return count > 0;
    }

    @Override
    public Long findPointsById(Long memberId) {
        Long points = entityManager.createQuery("SELECT point FROM Member WHERE id = :memberId", Long.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
        return points;
    }


}