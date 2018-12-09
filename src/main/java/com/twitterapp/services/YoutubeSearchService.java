package com.twitterapp.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

@Service
public class YoutubeSearchService {
	private static final String YOUTUBE_BASEURL = "https://www.youtube.com/";
	
	private static  YouTube youtube;
	
	public List<String> getVideosForArtist(String artistName,String key,String profileUrl) throws IOException{
		 youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
             public void initialize(HttpRequest request) throws IOException {
             }
         }).setApplicationName("smartbeats").build();
		 YouTube.Search.List search = youtube.search().list("id");
		 search.setKey(key);
		 search.setQ(artistName+"VEVO");
		 search.setMaxResults(10L);
		 search.setType("channel");
		 search.setTopicId("/m/04rlf");
		 System.out.println(artistName+ "name");
		 SearchListResponse searchResponse = search.execute();
         List<SearchResult> searchResultList = searchResponse.getItems();
         for(SearchResult result : searchResultList) {
        	 String channelId = result.getId().getChannelId();
        	 System.out.println(channelId);
        	 if(verifyArtist(channelId,profileUrl)) {
        		 YouTube.Search.List videos = youtube.search().list("id");
        		 videos.setKey(key);
        		 videos.setMaxResults(10L);
        		 videos.setType("video");
        		 videos.setVideoCategoryId("10");
        		 videos.setOrder("viewCount");
        		 videos.setChannelId(channelId);
        		 SearchListResponse videoResponse = videos.execute();
                 List<SearchResult> videoResults = videoResponse.getItems();
                 List<String> videoIds = new ArrayList<String>();
                 for(SearchResult video :  videoResults) {
                	 videoIds.add(video.getId().getVideoId());
                 }
                 return videoIds;
        	 }
         }
         return null;
	}
	
	private boolean verifyArtist(String channelId,String profileUrl) throws IOException {
		Document doc = Jsoup.connect(YOUTUBE_BASEURL+"channel/"+channelId+"/about").timeout(10000).get();
		for(Element ele : doc.select("a")) {
		String twitterLink = ele.attr("href").toLowerCase();
		if(twitterLink.contains("www.")) {
			twitterLink = twitterLink.replace("www.", "");
		}
		if(twitterLink.equalsIgnoreCase((profileUrl))) {
			System.out.println(twitterLink +"and"+ profileUrl );
			return true;
		}
		}
		return false;
	}
}
