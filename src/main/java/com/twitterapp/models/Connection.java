package com.twitterapp.models;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

public class Connection implements Serializable {
	private static final long serialVersionUID = 1L;
	private TwitterTemplate twitterTemplate;
	private OAuth1Operations oAuth1Operations;
	private OAuthToken requestToken;
	
	public Connection(TwitterTemplate twitterTemplate, OAuth1Operations oAuth1Operations, OAuthToken requestToken) {
		this.twitterTemplate = twitterTemplate;
		this.oAuth1Operations = oAuth1Operations;
		this.requestToken = requestToken;
	}
	public TwitterTemplate getTwitterTemplate() {
		return twitterTemplate;
	}
	public void setTwitterTemplate(TwitterTemplate twitterTemplate) {
		this.twitterTemplate = twitterTemplate;
	}
	public OAuth1Operations getoAuth1Operations() {
		return oAuth1Operations;
	}
	public void setoAuth1Operations(OAuth1Operations oAuth1Operations) {
		this.oAuth1Operations = oAuth1Operations;
	}
	public OAuthToken getRequestToken() {
		return requestToken;
	}
	public void setRequestToken(OAuthToken requestToken) {
		this.requestToken = requestToken;
	}
}
