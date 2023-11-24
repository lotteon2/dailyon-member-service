package com.dailyon.memeberservice.member.repository.custom;

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
}
