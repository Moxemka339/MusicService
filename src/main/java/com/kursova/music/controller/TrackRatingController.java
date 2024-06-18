package com.kursova.music.controller;

import com.kursova.music.model.TrackRating;
import com.kursova.music.service.TrackRatingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
@Tag(name = "TrackRating", description = "Operations related to TrackRating")
public class TrackRatingController {
    @Autowired
    private TrackRatingService trackRatingService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public TrackRating rateTrack(@RequestParam Long trackId, @RequestParam Long userId, @RequestParam Integer rating) {
        return trackRatingService.rateTrack(trackId, userId, rating);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping
    public Integer getRatingForTrackByUser(@RequestParam Long trackId, @RequestParam Long userId) {
        return trackRatingService.getRatingForTrackByUser(trackId, userId);
    }


}
