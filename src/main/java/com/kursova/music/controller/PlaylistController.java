package com.kursova.music.controller;

import com.kursova.music.model.Playlist;
import com.kursova.music.model.User;
import com.kursova.music.service.PlaylistService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@Tag(name = "Playlist", description = "Operations related to Playlists")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public Playlist createPlaylist(@RequestParam String name, @RequestParam Long userId) {
        return playlistService.createPlaylist(name, userId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping("/{playlistId}/tracks")
    public Playlist addTrackToPlaylist(@PathVariable Long playlistId, @RequestParam Long trackId, @RequestParam Integer order) {
        return playlistService.addTrackToPlaylist(playlistId, trackId, order);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping("/{playlistId}/access")
    public void giveAccessToPlaylist(@PathVariable Long playlistId, @RequestParam Long userId) {
        playlistService.giveAccessToPlaylist(playlistId, userId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public Playlist getPlaylistById(@PathVariable Long id) {
        return playlistService.getPlaylistById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{playlistId}/users")
    public List<User> getUsersWithAccessToPlaylist(@PathVariable Long playlistId) {
        return playlistService.getUsersWithAccessToPlaylist(playlistId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PatchMapping("/{id}")
    public Playlist updatePlaylistName(@PathVariable Long id, @RequestParam String name) {
        return playlistService.updatePlaylistName(id, name);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
    }
}
