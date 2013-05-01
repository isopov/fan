package com.sopovs.moradanen.fan.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sopovs.moradanen.fan.domain.infra.User;

@Repository(value = "userDetailsService")
@Transactional
public class FanUserDetailsService implements IFanUserDetailsService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> from = cq.from(User.class);
            cq.where(cb.equal(from.get("username"), username));

            return em.createQuery(cq).getSingleResult();
        } catch (NoResultException noResult) {
            throw new UsernameNotFoundException(username + " user ont found");
        }
    }

    @Override
    public void saveUser(User user) {
        em.persist(user);
    }

    @Override
    public boolean checkEmailNotUsed(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<User> from = cq.from(User.class);
        cq.select(cb.count(from));
        cq.where(cb.equal(from.get("email"), email));
        return em.createQuery(cq).getSingleResult().intValue() == 0;
    }

    @Override
    public boolean checkUsernameNotUsed(String username) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<User> from = cq.from(User.class);
        cq.select(cb.count(from));
        cq.where(cb.equal(from.get("username"), username));
        return em.createQuery(cq).getSingleResult().intValue() == 0;
    }

    @Override
    public boolean checkVisibleNameNotUsed(String visibleName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<User> from = cq.from(User.class);
        cq.select(cb.count(from));
        cq.where(cb.equal(from.get("visibleName"), visibleName));
        return em.createQuery(cq).getSingleResult().intValue() == 0;
    }

    @Override
    public User loadUserById(Long id) {
        return em.find(User.class, id);
    }
}
