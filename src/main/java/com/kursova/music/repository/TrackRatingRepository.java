package com.kursova.music.repository;

import com.kursova.music.model.Track;
import com.kursova.music.model.TrackRating;
import com.kursova.music.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRatingRepository extends JpaRepository<TrackRating, Long> {
    TrackRating findByTrackAndUser(Track track, User user);
//    TrackRating findByTrackIdAndUserId(Long trackId, Long userId);
}
