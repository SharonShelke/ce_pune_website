package com.christembassy.pune;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/youtube")
public class YouTubeController {

    @Autowired
    private YouTubeService youTubeService;

    @GetMapping("/latest")
    public String getLatestVideo() throws Exception {
        return youTubeService.getLatestVideo();
    }

    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        return youTubeService.getStatus();
    }
}
