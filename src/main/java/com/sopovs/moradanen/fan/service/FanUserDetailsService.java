package com.sopovs.moradanen.fan.service;

import com.sopovs.moradanen.fan.domain.Club;
import com.sopovs.moradanen.fan.domain.infra.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository(value = "userDetailsService")
@Transactional
public class FanUserDetailsService implements UserDetailsService {

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
}
