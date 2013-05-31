package com.sopovs.moradanen.fan.domain.infra;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ForeignKey;
import org.springframework.social.connect.jpa.RemoteUser;

import com.sopovs.moradanen.fan.domain.AbstractEntity;

@Entity
@Table(name = "USER_SOCIAL_CONNECTION")
@Getter
@Setter
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

    // @Column(name = "IRANK")
    // private int rank;

    public UserSocialConnection() {
    }

    @Override
    public String getUserId() {
        return getId().toString();
    }

    @Override
    public void setUserId(String id) {
        setId(Long.valueOf(id));
    }

}
