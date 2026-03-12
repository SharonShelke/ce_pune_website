package com.christembassy.pune;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class YouTubeService {

    @Value("${youtube.api.key}")
    private String apiKey;

    @Value("${youtube.channel.id}")
    private String channelId;

    public String getLatestVideo() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // 1. Check for live video
        String liveUrl = "https://www.googleapis.com/youtube/v3/search"
                + "?key=" + apiKey
                + "&channelId=" + channelId
                + "&part=snippet,id"
                + "&eventType=live"
                + "&type=video"
                + "&maxResults=1";

        try {
            System.out.println("Checking for live video with URL: " + liveUrl);
            String liveResponse = restTemplate.getForObject(liveUrl, String.class);
            System.out.println("Live API Response: " + liveResponse);
            
            JSONObject liveJson = new JSONObject(liveResponse);
            JSONArray liveItems = liveJson.getJSONArray("items");

            if (liveItems.length() > 0) {
                System.out.println("Live video found!");
                JSONObject video = liveItems.getJSONObject(0);
                return video.getJSONObject("id").getString("videoId");
            } else {
                 System.out.println("No live video found in response. Checking for upcoming streams...");
                 String upcomingUrl = "https://www.googleapis.com/youtube/v3/search"
                         + "?key=" + apiKey
                         + "&channelId=" + channelId
                         + "&part=snippet,id"
                         + "&eventType=upcoming"
                         + "&type=video"
                         + "&maxResults=1";
                 String upcomingResponse = restTemplate.getForObject(upcomingUrl, String.class);
                 JSONObject upcomingJson = new JSONObject(upcomingResponse);
                 JSONArray upcomingItems = upcomingJson.getJSONArray("items");
                 
                 if (upcomingItems.length() > 0) {
                     System.out.println("Upcoming video found!");
                     JSONObject video = upcomingItems.getJSONObject(0);
                     return video.getJSONObject("id").getString("videoId");
                 }
            }
        } catch (Exception e) {
            System.err.println("Error fetching live video, falling back to latest: " + e.getMessage());
            e.printStackTrace();
        }

        // 2. Fallback to latest video
        String url = "https://www.googleapis.com/youtube/v3/search"
                + "?key=" + apiKey
                + "&channelId=" + channelId
                + "&part=snippet,id"
                + "&order=date"
                + "&maxResults=1";

        String response = restTemplate.getForObject(url, String.class);

        JSONObject json = new JSONObject(response);
        JSONArray items = json.getJSONArray("items");

        JSONObject video = items.getJSONObject(0);
        String videoId = video.getJSONObject("id").getString("videoId");

        return videoId;
    }
}