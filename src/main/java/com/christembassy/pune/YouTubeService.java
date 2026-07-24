package com.christembassy.pune;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class YouTubeService {

    @Value("${youtube.api.key}")
    private String apiKey;

    @Value("${youtube.channel.id}")
    private String channelId;

    private Map<String, Object> cachedStatus = null;
    private long lastFetchTime = 0;
    private static final long CACHE_DURATION = 5 * 60 * 1000; // 5 minutes

    public Map<String, Object> getStatus() {
        if (cachedStatus != null && (System.currentTimeMillis() - lastFetchTime < CACHE_DURATION)) {
            return cachedStatus;
        }

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> result = new HashMap<>();
        result.put("isLive", false);
        result.put("videoId", "VjmBpyecvIo"); // Fallback

        try {
            // 1. Check for live video
            String liveUrl = "https://www.googleapis.com/youtube/v3/search"
                    + "?key=" + apiKey
                    + "&channelId=" + channelId
                    + "&part=snippet,id"
                    + "&eventType=live"
                    + "&type=video"
                    + "&maxResults=1";

            System.out.println("Checking for live video with URL: " + liveUrl);
            String liveResponse = restTemplate.getForObject(liveUrl, String.class);
            JSONObject liveJson = new JSONObject(liveResponse);
            JSONArray liveItems = liveJson.getJSONArray("items");

            if (liveItems.length() > 0) {
                System.out.println("Live video found!");
                JSONObject video = liveItems.getJSONObject(0);
                result.put("videoId", video.getJSONObject("id").getString("videoId"));
                result.put("isLive", true);
                
                cachedStatus = result;
                lastFetchTime = System.currentTimeMillis();
                return result;
            }

            // 2. Check for upcoming streams
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
                result.put("videoId", video.getJSONObject("id").getString("videoId"));
                result.put("isLive", false);
                
                cachedStatus = result;
                lastFetchTime = System.currentTimeMillis();
                return result;
            }

            // 3. Fallback to latest uploaded video if not live and not upcoming
            System.out.println("No upcoming video found. Checking for latest uploaded video...");
            String latestUrl = "https://www.googleapis.com/youtube/v3/search"
                    + "?key=" + apiKey
                    + "&channelId=" + channelId
                    + "&part=snippet,id"
                    + "&order=date"
                    + "&type=video"
                    + "&maxResults=1";
            String latestResponse = restTemplate.getForObject(latestUrl, String.class);
            JSONObject latestJson = new JSONObject(latestResponse);
            JSONArray latestItems = latestJson.getJSONArray("items");

            if (latestItems.length() > 0) {
                System.out.println("Latest uploaded video found!");
                JSONObject video = latestItems.getJSONObject(0);
                result.put("videoId", video.getJSONObject("id").getString("videoId"));
                result.put("isLive", false);
                
                cachedStatus = result;
                lastFetchTime = System.currentTimeMillis();
                return result;
            }
            
        } catch (Exception e) {
            System.err.println("Error fetching YouTube status: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Returning fallback video: VjmBpyecvIo");
        cachedStatus = result;
        lastFetchTime = System.currentTimeMillis();
        return result;
    }

    // Keep backward compatibility for /latest
    public String getLatestVideo() {
        return (String) getStatus().get("videoId");
    }
}
