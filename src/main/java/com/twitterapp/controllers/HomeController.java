package com.twitterapp.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.util.Pair;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.twitterapp.DTO.UserDTO;
import com.twitterapp.models.Connection;
import com.twitterapp.models.User;
import com.twitterapp.repositories.UserRepository;
import com.twitterapp.services.YoutubeSearchService;



@Controller
@PropertySource({"classpath:/twitter.properties","classpath:/youtube.properties"})
public class HomeController {
	/*@Autowired
	private SearchService searchService;*/
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private HttpSession session;
	@Autowired
	private YoutubeSearchService youTubeSearchService;
	@Value("${consumerkey}")
	String CONSUMERKEY; 
	@Value("${consumersecret}")
	String CONSUMERSECRET;
	@Value("${youtube.apikey}")
	String key;
	private static final String YOUTUBE_BASEURL = "https://www.youtube.com/";
	Map<String,Connection > map = new HashMap<String, Connection>();
	
	@RequestMapping("/home")
	public String getHomePage() {
		return "homePage.jsp";
	}
	@RequestMapping("/callback")
	public String getLoggedInPage(@RequestParam(value="oauth_token") String token,@RequestParam(value="oauth_verifier") String verifier,HttpServletRequest request, HttpServletResponse response) {
		System.out.println(session.getId());
		OAuthToken accessToken = /*((Connection)session.getAttribute("connection"))*/map.get(token).getoAuth1Operations()
				.exchangeForAccessToken(new AuthorizedRequestToken(/*((Connection)session.getAttribute("connection"))*/map.get(token).getRequestToken(), verifier), null);
		TwitterTemplate twitterTemplate = initializeTwitterTemplate(accessToken);
		map.get(token).setTwitterTemplate(twitterTemplate);
		//((Connection)session.getAttribute("connection")).setTwitterTemplate(twitterTemplate);
		for(Cookie c : request.getCookies()) {
			System.out.println(c.getName()+" "+c.getValue()+" "+c.getDomain());
		}
		return "index.jsp";
	}
	@RequestMapping("/signin")
	public void signInToTwitter(HttpServletResponse response) throws IOException {
		System.out.println(CONSUMERKEY+" "+CONSUMERSECRET);
		TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(CONSUMERKEY, CONSUMERSECRET);
	    OAuth1Operations oAuth1Operations = connectionFactory.getOAuthOperations();
		OAuthToken requestToken = oAuth1Operations.fetchRequestToken("", null);
		map.put(requestToken.getValue(),new Connection(null,oAuth1Operations,requestToken));
		session.setAttribute("connection", new Connection(null,oAuth1Operations,requestToken));
		System.out.println(oAuth1Operations.hashCode()+ "-"+requestToken.getValue());
		System.out.println(session.getId()+" "+session.getAttribute("connection").toString());
		Cookie c = new Cookie("Vivek","Trial");
		
		c.setDomain("api.twitter.com");
		response.addCookie(c);
		response.sendRedirect("https://api.twitter.com/oauth/authorize?oauth_token="+requestToken.getValue());
	}
	@RequestMapping("/viewFriends")
	@ResponseBody
	public List<TwitterProfile> viewFriends(){
		System.out.println("friends " + session.getId());
		Connection connection = ((Connection)session.getAttribute("connection"));
		return map.get(connection.getRequestToken().getValue()).getTwitterTemplate().friendOperations().getFriends();
	}
	public TwitterTemplate initializeTwitterTemplate(OAuthToken accessToken) {
		return new TwitterTemplate(CONSUMERKEY, CONSUMERSECRET, accessToken.getValue(), accessToken.getSecret());
	}
	
	@RequestMapping("/updateLastSong")
	public void updateLastSong(@RequestParam(value="videoId")String videoId) {
		((Connection)session.getAttribute("connection")).getTwitterTemplate().userOperations().getProfileId();
	}
	
	@RequestMapping("/saveUser")
	@ResponseBody
	public String saveUser() {
		System.out.println("save user "+session.getId());
		Connection connection = (Connection)session.getAttribute("connection");
		User user = new User();
		user.setId(map.get(connection.getRequestToken().getValue()).getTwitterTemplate().userOperations().getProfileId());
		user.setName(map.get(connection.getRequestToken().getValue()).getTwitterTemplate().userOperations().getScreenName());
		List<String> list = new ArrayList<>();
		list.add("Chandelier");
		user.setFollowingIds(map.get(connection.getRequestToken().getValue()).getTwitterTemplate().friendOperations().getFriendIds());
		Map<String,List<String> > map = new HashMap<>();
		map.put("Sia", list);
		user.setSongs(map);
		return userRepository.saveUser(user);
	}
	
	@RequestMapping("/checkArtists")
	private String checkArtists(Model model) throws IOException {
		Connection connection = ((Connection)session.getAttribute("connection"));
		List<TwitterProfile> accounts = map.get(connection.getRequestToken().getValue()).getTwitterTemplate().friendOperations().getFriends();
		List<List<String>> out = new ArrayList<List<String>>();
		UserDTO user = userRepository.getUser(map.get(connection.getRequestToken().getValue()).getTwitterTemplate().userOperations().getProfileId());
		List<Long> ll = user.getFollowingIds();
		for(TwitterProfile profile : accounts) {
			if(!ll.contains(profile.getId())) {
			if(profile.isVerified() && profile.getFollowersCount()>50000) {
				System.out.println(profile.getProfileUrl());
				List<String> list = youTubeSearchService.getVideosForArtist(profile.getScreenName(), key, profile.getProfileUrl());
				if(list==null) {
					System.out.println("Null");
				}
				else {
				System.out.println(list.size());
				out.add(list);
				}
			}
			}
		}
		model.addAttribute("videos",out);
		return "loggedInHomePage.jsp";
	}
	
}

