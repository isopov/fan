package com.sopovs.moradanen.fan.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.jpa.JpaTemplate;
import org.springframework.social.connect.jpa.RemoteUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.sopovs.moradanen.fan.domain.infra.User;
import com.sopovs.moradanen.fan.domain.infra.UserSocialConnection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:coreApplicationContext.xml")
@Transactional
public class UserSocialConnectionServiceTest {

    @Autowired
    private JpaTemplate service;

    @Autowired
    private IFanUserDetailsService userDetailsService;

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unused")
    private String id1, id2, id3, twitterId;

    @Before
    public void setUp() {
        User notConnectedUser = new User("notConnectedUser");
        userDetailsService.saveUser(notConnectedUser);

        id1 = createConnectedUser("facebookConnectedUser1", "facebook", "test_facebook_id1");
        id2 = createConnectedUser("facebookConnectedUser2", "facebook", "test_facebook_id2");
        id3 = createConnectedUser("facebookConnectedUser3", "facebook", "test_facebook_id3");
        twitterId = createConnectedUser("twitterConnectedUser", "twitter", "test_twitter_id");

    }

    private String createConnectedUser(String username, String providerId, String provideruserId) {
        User connectedUser = new User(username);
        UserSocialConnection connection = new UserSocialConnection();
        connection.setProviderId(providerId);
        connection.setProviderUserId(provideruserId);
        connection.setUser(connectedUser);
        connectedUser.setSocialConnections(Arrays.asList(connection));
        userDetailsService.saveUser(connectedUser);

        return connectedUser.getId().toString();
    }

    @Test
    public void testFindUsersConnectedTo() {
        Set<String> actual = service.findUsersConnectedTo("facebook",
                Sets.newHashSet("test_facebook_id1", "test_facebook_id2"));
        assertEquals(Sets.newHashSet(id1, id2), actual);
    }

    @Test
    public void testGetPrimary() {
        List<RemoteUser> actual = service.getPrimary(id1.toString(), "facebook");
        assertEquals(1, actual.size());
        assertEquals("test_facebook_id1", actual.get(0).getProviderUserId());
    }

    @Test
    public void testGetAll() {
        List<RemoteUser> actual = service.getAll(id1.toString(), "facebook");
        assertEquals(1, actual.size());
        assertEquals("test_facebook_id1", actual.get(0).getProviderUserId());
    }

}
