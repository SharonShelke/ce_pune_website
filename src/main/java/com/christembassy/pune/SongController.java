package com.christembassy.pune;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = "*")
public class SongController {

    @Autowired
    private SongRepository songRepository;

    @GetMapping
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    @GetMapping("/{id}/stream")
    public ResponseEntity<Resource> streamSong(@PathVariable Long id) {
        Song song = songRepository.findById(id).orElse(null);
        if (song == null || song.getDriveId() == null) {
            return ResponseEntity.notFound().build();
        }

        String driveUrl = "https://docs.google.com/uc?export=download&id=" + song.getDriveId();
        try {
            RestTemplate restTemplate = new RestTemplate();
            byte[] audioData = restTemplate.getForObject(driveUrl, byte[].class);
            ByteArrayResource resource = new ByteArrayResource(audioData);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("audio/mpeg"))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
