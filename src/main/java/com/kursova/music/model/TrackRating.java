package com.kursova.music.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "track_ratings")
public class TrackRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Long ratingId;

    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "rating")
    private Integer rating;
}
