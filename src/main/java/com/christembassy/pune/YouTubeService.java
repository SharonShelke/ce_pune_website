package com.christembassy.pune;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

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

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000); // 3 seconds
        factory.setReadTimeout(3000); // 3 seconds
        RestTemplate restTemplate = new RestTemplate(factory);
        Map<String, Object> result = new HashMap<>();
        result.put("isLive", false);
        result.put("videoId", "VjmBpyecvIo"); // Fallback

        try {
            System.out.println("Checking YouTube live status via Playlist API for channel: " + channelId);
            
            // The uploads playlist ID is the channel ID with 'UU' instead of 'UC'
            String uploadsPlaylistId = "UU" + channelId.substring(2);
            
            // 1. Fetch the latest 3 videos from the uploads playlist (Cost: 1 quota point)
            String playlistUrl = "https://www.googleapis.com/youtube/v3/playlistItems"
                    + "?part=snippet"
                    + "&playlistId=" + uploadsPlaylistId
                    + "&maxResults=3"
                    + "&key=" + apiKey;

            String playlistResponse = restTemplate.getForObject(playlistUrl, String.class);
            JSONObject playlistJson = new JSONObject(playlistResponse);
            JSONArray playlistItems = playlistJson.getJSONArray("items");

            if (playlistItems.length() > 0) {
                // Collect video IDs
                StringBuilder videoIds = new StringBuilder();
                for (int i = 0; i < playlistItems.length(); i++) {
                    JSONObject item = playlistItems.getJSONObject(i);
                    String vId = item.getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
                    if (i > 0) videoIds.append(",");
                    videoIds.append(vId);
                }

                // 2. Check the live status of these videos (Cost: 1 quota point)
                String videosUrl = "https://www.googleapis.com/youtube/v3/videos"
                        + "?part=snippet,liveStreamingDetails"
                        + "&id=" + videoIds.toString()
                        + "&key=" + apiKey;

                String videosResponse = restTemplate.getForObject(videosUrl, String.class);
                JSONObject videosJson = new JSONObject(videosResponse);
                JSONArray videosItems = videosJson.getJSONArray("items");

                String latestVideoId = null;
                String upcomingVideoId = null;

                for (int i = 0; i < videosItems.length(); i++) {
                    JSONObject video = videosItems.getJSONObject(i);
                    String vId = video.getString("id");
                    String broadcastContent = video.getJSONObject("snippet").getString("liveBroadcastContent");
                    
                    if (latestVideoId == null) {
                        latestVideoId = vId; // Save the most recent one as fallback
                    }

                    if ("live".equals(broadcastContent)) {
                        System.out.println("Live video found! Video ID: " + vId);
                        result.put("isLive", true);
                        result.put("videoId", vId);
                        
                        cachedStatus = result;
                        lastFetchTime = System.currentTimeMillis();
                        return result;
                    } else if ("upcoming".equals(broadcastContent)) {
                        if (upcomingVideoId == null) {
                            upcomingVideoId = vId; // Save the upcoming one
                        }
                    }
                }

                // If not live, fall back to upcoming, or latest video
                if (upcomingVideoId != null) {
                    System.out.println("No live video found, but upcoming found: " + upcomingVideoId);
                    result.put("isLive", false);
                    result.put("videoId", upcomingVideoId);
                } else if (latestVideoId != null) {
                    System.out.println("No live video found, using latest video: " + latestVideoId);
                    result.put("isLive", false);
                    result.put("videoId", latestVideoId);
                }
            } else {
                System.out.println("No videos found in the uploads playlist.");
            }
        } catch (Exception e) {
            System.err.println("Error checking YouTube status: " + e.getMessage());
            e.printStackTrace();
        }

        cachedStatus = result;
        lastFetchTime = System.currentTimeMillis();
        return result;
    }

    public String getLatestVideo() {
        return (String) getStatus().get("videoId");
    }
}
