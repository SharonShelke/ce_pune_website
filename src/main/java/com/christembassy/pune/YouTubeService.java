package com.christembassy.pune;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class YouTubeService {

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
            System.out.println("Scraping YouTube live status for channel: " + channelId);
            String url = "https://www.youtube.com/channel/" + channelId + "/live";
            
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
            headers.set("Accept-Language", "en-US,en;q=0.9");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String html = response.getBody();
            
            if (html != null) {
                // Find canonical URL
                Pattern pattern = Pattern.compile("<link rel=\"canonical\" href=\"(https://www.youtube.com/watch\\?v=[^\"]+)\">");
                Matcher matcher = pattern.matcher(html);
                
                if (matcher.find()) {
                    String canonicalUrl = matcher.group(1);
                    String videoId = canonicalUrl.split("v=")[1];
                    System.out.println("Found video page: " + videoId);
                    
                    if (html.contains("\"isLiveNow\":true")) {
                        System.out.println("Video is currently LIVE!");
                        result.put("isLive", true);
                        result.put("videoId", videoId);
                    } else if (html.contains("\"isUpcoming\":true")) {
                        System.out.println("Video is UPCOMING.");
                        result.put("isLive", false);
                        result.put("videoId", videoId);
                    } else {
                        System.out.println("Video is a past VOD or not live.");
                        result.put("isLive", false);
                        result.put("videoId", videoId);
                    }
                } else {
                    System.out.println("No watch page canonical link found. Channel is not live.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error scraping YouTube status: " + e.getMessage());
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
