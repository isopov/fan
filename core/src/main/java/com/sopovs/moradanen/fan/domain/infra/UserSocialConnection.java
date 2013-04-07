package com.sopovs.moradanen.fan.domain.infra;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.springframework.social.connect.jpa.RemoteUser;

import com.sopovs.moradanen.fan.domain.AbstractEntity;

@Entity
@Table(name = "USER_SOCIAL_CONNECTION")
public class UserSocialConnection extends AbstractEntity implements RemoteUser {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @ForeignKey(name = "USER_SOCIAL_USER_FK")
    private User user;

    private String providerUserId;
    private String providerId;
    private String secret;
    private String profileUrl;
    private String imageUrl;
    private String accessToken;
    private String refreshToken;
    private String displayName;
    private Long expireTime;
    @Column(name = "IRANK")
    private int rank;

    public UserSocialConnection() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getUserId() {
        return getId().toString();
    }

    @Override
    public void setUserId(String id) {
        setId(UUID.fromString(id));
    }

    @Override
    public String getProviderUserId() {
        return providerUserId;
    }

    @Override
    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    @Override
    public String getProviderId() {
        return providerId;
    }

    @Override
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Override
    public String getSecret() {
        return secret;
    }

    @Override
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String getProfileUrl() {
        return profileUrl;
    }

    @Override
    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public Long getExpireTime() {
        return expireTime;
    }

    @Override
    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public int getRank() {
        return rank;
    }

    @Override
    public void setRank(int rank) {
        this.rank = rank;
    }

}
