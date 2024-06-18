package com.kursova.music.service;

import com.kursova.music.model.Track;
import com.kursova.music.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackService {
    @Autowired
    private TrackRepository trackRepository;

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public Track saveTrack(Track track) {
        return trackRepository.save(track);
    }

    public Track getTrackById(Long id) {
        return trackRepository.findById(id).orElse(null);
    }

    public Track updateTrack(Long id, Track updatedTrack) {
        Track track = trackRepository.findById(id).orElseThrow(() -> new RuntimeException("Track not found"));
        track.setTitle(updatedTrack.getTitle());
        track.setArtist(updatedTrack.getArtist());
        track.setAlbum(updatedTrack.getAlbum());
        track.setGenre(updatedTrack.getGenre());
        track.setFilePath(updatedTrack.getFilePath());
        return trackRepository.save(track);
    }

    public void deleteTrack(Long id) {
        trackRepository.deleteById(id);
    }
}

