package com.kursova.music.repository;

import com.kursova.music.model.Playlist;
import com.kursova.music.model.PlaylistAccess;
import com.kursova.music.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistAccessRepository extends JpaRepository<PlaylistAccess, Long> {
    List<PlaylistAccess> findByPlaylist(Playlist playlist);
    List<PlaylistAccess> findByUser(User user);
}
