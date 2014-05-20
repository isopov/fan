package com.sopovs.moradanen.fan;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jpa.JpaTemplate;
import org.springframework.social.connect.jpa.JpaUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import com.sopovs.moradanen.fan.security.SimpleSignInAdapter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

@Configuration
@EnableSocial
public class SocialConfiguration implements SocialConfigurer {

	@Autowired
	private JpaTemplate jpaTemplate;


	@Value("${facebook.appid}")
	private String facebookAppId;

	@Value("${facebook.secret}")
	private String facebookSecret;

	@Value("${facebook.namespace}")
	private String facebookNamespace;

	@Value("${twitter.appid}")
	private String twitterAppId;

	@Value("${twitter.secret}")
	private String twitterSecret;

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
		cfConfig.addConnectionFactory(new FacebookConnectionFactory(
				facebookAppId,
				facebookSecret,
				facebookNamespace));
		cfConfig.addConnectionFactory(new TwitterConnectionFactory(
				twitterAppId,
				twitterSecret));
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}


	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		return new JpaUsersConnectionRepository(jpaTemplate, connectionFactoryLocator, textEncryptor());
	}

	@Bean
	public TextEncryptor textEncryptor() {
		// TODO use encryption!
		return Encryptors.noOpText();
	}

	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.NO)
	@Autowired
	public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator,
	                                           ConnectionRepository connectionRepository) {
		return new ConnectController(connectionFactoryLocator, connectionRepository);
	}

	@Bean
	@Autowired
	public ProviderSignInController psc(ConnectionFactoryLocator connectionFactoryLocator,
	                                    UsersConnectionRepository usersConnectionRepository, SignInAdapter signInAdapter) {
		return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, signInAdapter);
	}

	@Bean
	@Autowired
	public SignInAdapter signInAdapter(RequestCache requestCache) {
		return new SimpleSignInAdapter(requestCache);
	}

	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.NO)
	@Autowired
	public ConnectionRepository connectionRepository(UsersConnectionRepository usersConnectionRepository,
	                                                 HttpServletRequest request) {
		// TODO request.userPrincipal.principal.id.toString()
		return usersConnectionRepository.createConnectionRepository(((Authentication) request.getUserPrincipal())
				.getPrincipal().toString());
	}
}
