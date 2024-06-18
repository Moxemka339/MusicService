package com.kursova.music.repository;

import com.kursova.music.model.Playlist;
import com.kursova.music.model.PlaylistTrack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, Long> {
    List<PlaylistTrack> findByPlaylist(Playlist playlist);

}
