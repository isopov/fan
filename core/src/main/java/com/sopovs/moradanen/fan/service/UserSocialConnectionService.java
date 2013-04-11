package com.sopovs.moradanen.fan.service;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.social.connect.jpa.JpaTemplate;
import org.springframework.social.connect.jpa.RemoteUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sopovs.moradanen.fan.domain.infra.User;
import com.sopovs.moradanen.fan.domain.infra.UserSocialConnection;

@Repository
@Transactional
public class UserSocialConnectionService implements JpaTemplate {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Set<String> findUsersConnectedTo(String providerId, Set<String> providerUserIds) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UUID> cq = cb.createQuery(UUID.class);
        Root<UserSocialConnection> from = cq.from(UserSocialConnection.class);
        cq.where(cb.and(cb.equal(from.get("providerId"), providerId), from.get("providerUserId").in(providerUserIds)));
        cq.select(from.get("user").<UUID> get("id"));
        return Sets.newHashSet(toStringList(em.createQuery(cq).getResultList()));
    }

    @Override
    public List<RemoteUser> getPrimary(String userId, String providerId) {
        return getAll(userId, providerId);
    }

    // @Override
    // public int getRank(String userId, String providerId) {
    // CriteriaBuilder cb = em.getCriteriaBuilder();
    // CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
    // Root<UserSocialConnection> from = cq.from(UserSocialConnection.class);
    // cq.select(from.<Integer> get("rank"));
    // cq.where(cb.and(cb.equal(from.get("providerId"), providerId),
    // cb.equal(from.get("user").get("id"), UUID.fromString(userId))));
    //
    // return em.createQuery(cq).getSingleResult();
    // }

    @Override
    public List<RemoteUser> getAll(String userId, MultiValueMap<String, String> providerUsers) {
        if (providerUsers.isEmpty()) {
            return Collections.<RemoteUser> emptyList();
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserSocialConnection> cq = cb.createQuery(UserSocialConnection.class);

        Root<UserSocialConnection> from = cq.from(UserSocialConnection.class);
        cq.where(cb.equal(from.get("user").get("id"), UUID.fromString(userId)));

        List<Predicate> usersRestrictions = Lists.newArrayListWithCapacity(providerUsers.size());
        for (Iterator<Entry<String, List<String>>> it = providerUsers
                .entrySet().iterator(); it.hasNext();) {
            Entry<String, List<String>> entry = it.next();
            String providerId = entry.getKey();
            Predicate and = cb.and(cb.equal(from.get("providerId"), providerId),
                    from.get("providerUserId").in(entry.getValue()));
            usersRestrictions.add(and);
        }
        cq.where(cb.and(cb.equal(from.get("user").get("id"), UUID.fromString(userId)),
                cb.or(usersRestrictions.toArray(new Predicate[usersRestrictions.size()]))));

        return Lists.<RemoteUser> newArrayList(em.createQuery(cq).getResultList());
    }

    @Override
    public List<RemoteUser> getAll(String userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserSocialConnection> cq = cb.createQuery(UserSocialConnection.class);
        Root<UserSocialConnection> from = cq.from(UserSocialConnection.class);
        cq.where(cb.equal(from.get("user").get("id"), UUID.fromString(userId)));
        return Lists.<RemoteUser> newArrayList(em.createQuery(cq).getResultList());
    }

    @Override
    public List<RemoteUser> getAll(String userId, String providerId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserSocialConnection> cq = cb.createQuery(UserSocialConnection.class);
        Root<UserSocialConnection> from = cq.from(UserSocialConnection.class);
        cq.where(cb.and(cb.equal(from.get("providerId"), providerId),
                cb.equal(from.get("user").get("id"), UUID.fromString(userId))));

        return Lists.<RemoteUser> newArrayList(em.createQuery(cq).getResultList());
    }

    @Override
    public RemoteUser get(String userId, String providerId, String providerUserId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserSocialConnection> cq = cb.createQuery(UserSocialConnection.class);
        Root<UserSocialConnection> from = cq.from(UserSocialConnection.class);
        cq.where(cb.and(cb.equal(from.get("providerId"), providerId),
                cb.equal(from.get("providerUserId"), providerUserId),
                cb.equal(from.get("user").get("id"), UUID.fromString(userId))));

        return em.createQuery(cq).getSingleResult();
    }

    @Override
    public List<RemoteUser> get(String providerId, String providerUserId) throws IncorrectResultSizeDataAccessException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserSocialConnection> cq = cb.createQuery(UserSocialConnection.class);
        Root<UserSocialConnection> from = cq.from(UserSocialConnection.class);
        cq.where(cb.and(cb.equal(from.get("providerId"), providerId),
                cb.equal(from.get("providerUserId"), providerUserId)));

        return Lists.<RemoteUser> newArrayList(em.createQuery(cq).getResultList());

    }

    @Override
    public void remove(String userId, String providerId) {
        em.createQuery("delete from UserSocialConnection where provider=:providerId and user.id=:userId")
                .setParameter("providerId", providerId)
                .setParameter("userId", userId).executeUpdate();

    }

    @Override
    public void remove(String userId, String providerId, String providerUserId) {
        em.createQuery("delete from UserSocialConnection"
                + " where provider=:providerId"
                + " and user.id=:userId"
                + " and providerUserId=:providerUserId")
                .setParameter("providerId", providerId)
                .setParameter("providerUserId", providerUserId)
                .setParameter("userId", UUID.fromString(userId)).executeUpdate();

    }

    @Override
    public RemoteUser createRemoteUser(String userId, String providerId, String providerUserId,
            String displayName, String profileUrl, String imageUrl, String accessToken, String secret,
            String refreshToken, Long expireTime) {
        User user = em.find(User.class, UUID.fromString(userId));
        Preconditions.checkNotNull(user);

        UserSocialConnection connection = new UserSocialConnection();
        connection.setProviderId(providerId);
        connection.setProviderUserId(providerUserId);
        connection.setDisplayName(displayName);
        connection.setProfileUrl(profileUrl);
        connection.setImageUrl(imageUrl);
        connection.setAccessToken(accessToken);
        connection.setSecret(secret);
        connection.setRefreshToken(refreshToken);
        connection.setExpireTime(expireTime);

        if (user.getSocialConnections() == null) {
            user.setSocialConnections(Lists.<UserSocialConnection> newArrayListWithCapacity(1));
        }

        user.getSocialConnections().add(connection);
        em.persist(user);
        return connection;
    }

    @Override
    public RemoteUser save(RemoteUser user) {
        UserSocialConnection con = (UserSocialConnection) user;
        em.persist(con);
        return con;
    }

    private static <E> List<String> toStringList(List<E> list) {
        return Lists.transform(list, UUID_TO_STRING_FUNCTION);
    }

    private static final Function<Object, String> UUID_TO_STRING_FUNCTION = new Function<Object, String>() {
        @Override
        public String apply(Object input) {
            return input.toString();
        }
    };

}
