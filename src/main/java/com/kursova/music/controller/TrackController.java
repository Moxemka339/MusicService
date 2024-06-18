package com.kursova.music.controller;

import com.kursova.music.model.Track;
import com.kursova.music.service.TrackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracks")
@Tag(name = "Track", description = "Operations related to Tracks")
public class TrackController {
    @Autowired
    private TrackService trackService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping
    public List<Track> getAllTracks() {
        return trackService.getAllTracks();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public Track getTrackById(@PathVariable Long id) {
        return trackService.getTrackById(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public Track saveTrack(@RequestBody Track track) {
        return trackService.saveTrack(track);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PatchMapping("/{id}")
    public Track updateTrack(@PathVariable Long id, @RequestBody Track track) {
        return trackService.updateTrack(id, track);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTrack(@PathVariable Long id) {
        trackService.deleteTrack(id);
    }
}
