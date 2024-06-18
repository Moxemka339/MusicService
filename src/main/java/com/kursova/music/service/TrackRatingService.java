package com.kursova.music.service;

import com.kursova.music.model.Track;
import com.kursova.music.model.TrackRating;
import com.kursova.music.model.User;
import com.kursova.music.repository.TrackRatingRepository;
import com.kursova.music.repository.TrackRepository;
import com.kursova.music.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackRatingService {
    @Autowired
    private TrackRatingRepository trackRatingRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private UserRepository userRepository;

    public TrackRating rateTrack(Long trackId, Long userId, Integer rating) {
        Track track = trackRepository.findById(trackId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if (track == null || user == null) {
            throw new IllegalArgumentException("Track or User not found");
        }
        TrackRating trackRating = trackRatingRepository.findByTrackAndUser(track, user);
        if (trackRating == null) {
            trackRating = new TrackRating();
            trackRating.setTrack(track);
            trackRating.setUser(user);
        }
        trackRating.setRating(rating);
        return trackRatingRepository.save(trackRating);
    }

    public Integer getRatingForTrackByUser(Long trackId, Long userId) {
        Track track = trackRepository.findById(trackId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if (track == null || user == null) {
            throw new IllegalArgumentException("Track or User not found");
        }
        TrackRating trackRating = trackRatingRepository.findByTrackAndUser(track, user);
        return trackRating != null ? trackRating.getRating() : null;
    }
}
