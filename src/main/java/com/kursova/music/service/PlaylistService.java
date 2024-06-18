package com.kursova.music.service;

import com.kursova.music.model.*;
import com.kursova.music.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private PlaylistTrackRepository playlistTrackRepository;

    @Autowired
    private PlaylistAccessRepository playlistAccessRepository;

    public Playlist createPlaylist(String name, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        Playlist playlist = new Playlist();
        playlist.setPlaylistName(name);
        playlist.setCreatedBy(user);
        return playlistRepository.save(playlist);
    }

    public Playlist addTrackToPlaylist(Long playlistId, Long trackId, Integer order) {
        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        Track track = trackRepository.findById(trackId).orElse(null);
        if (playlist == null || track == null) {
            throw new IllegalArgumentException("Playlist or Track not found");
        }
        PlaylistTrack playlistTrack = new PlaylistTrack();
        playlistTrack.setPlaylist(playlist);
        playlistTrack.setTrack(track);
        playlistTrack.setTrackOrder(order);
        playlistTrackRepository.save(playlistTrack);
        return playlist;
    }

    public void giveAccessToPlaylist(Long playlistId, Long userId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if (playlist == null || user == null) {
            throw new IllegalArgumentException("Playlist or User not found");
        }
        PlaylistAccess playlistAccess = new PlaylistAccess();
        playlistAccess.setPlaylist(playlist);
        playlistAccess.setUser(user);
        playlistAccessRepository.save(playlistAccess);
    }

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist getPlaylistById(Long id) {
        Playlist playlist = playlistRepository.findById(id).orElse(null);
        if (playlist == null) {
            return null;
        }
        List<PlaylistTrack> playlistTracks = playlistTrackRepository.findByPlaylist(playlist);
        playlist.setPlaylistTracks(playlistTracks);
        return playlist;
    }

    public List<User> getUsersWithAccessToPlaylist(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        if (playlist == null) {
            throw new IllegalArgumentException("Playlist not found");
        }
        List<PlaylistAccess> accesses = playlistAccessRepository.findByPlaylist(playlist);
        return accesses.stream().map(PlaylistAccess::getUser).collect(Collectors.toList());
    }

    public Playlist updatePlaylistName(Long id, String name) {
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new RuntimeException("Playlist not found"));
        playlist.setPlaylistName(name);
        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
    }
}
