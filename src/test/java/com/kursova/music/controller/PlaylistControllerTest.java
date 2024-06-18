package com.kursova.music.controller;

import com.kursova.music.model.Playlist;
import com.kursova.music.model.User;
import com.kursova.music.service.PlaylistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PlaylistControllerTest {

    @Mock
    private PlaylistService playlistService;

    @InjectMocks
    private PlaylistController playlistController;

    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "USER")
    public void testCreatePlaylist() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(playlistController).build();
        Playlist playlist = new Playlist();
        playlist.setPlaylistName("Test Playlist");

        when(playlistService.createPlaylist(anyString(), anyLong())).thenReturn(playlist);

        mockMvc.perform(post("/api/playlists")
                        .param("name", "Test Playlist")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playlistName").value("Test Playlist"));

        verify(playlistService, times(1)).createPlaylist("Test Playlist", 1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllPlaylists() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(playlistController).build();
        Playlist playlist = new Playlist();
        playlist.setPlaylistName("Test Playlist");
        List<Playlist> playlists = Arrays.asList(playlist);

        when(playlistService.getAllPlaylists()).thenReturn(playlists);

        mockMvc.perform(get("/api/playlists"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].playlistName").value("Test Playlist"));

        verify(playlistService, times(1)).getAllPlaylists();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetPlaylistById() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(playlistController).build();
        Playlist playlist = new Playlist();
        playlist.setPlaylistName("Test Playlist");

        when(playlistService.getPlaylistById(anyLong())).thenReturn(playlist);

        mockMvc.perform(get("/api/playlists/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playlistName").value("Test Playlist"));

        verify(playlistService, times(1)).getPlaylistById(1L);
    }



    @Test
    @WithMockUser(roles = "USER")
    public void testDeletePlaylist() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(playlistController).build();

        mockMvc.perform(delete("/api/playlists/{id}", 1L))
                .andExpect(status().isOk());

        verify(playlistService, times(1)).deletePlaylist(1L);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testAddTrackToPlaylist() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(playlistController).build();
        Playlist playlist = new Playlist();
        playlist.setPlaylistName("Test Playlist");

        when(playlistService.addTrackToPlaylist(anyLong(), anyLong(), anyInt())).thenReturn(playlist);

        mockMvc.perform(post("/api/playlists/{playlistId}/tracks", 1L)
                        .param("trackId", "1")
                        .param("order", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playlistName").value("Test Playlist"));

        verify(playlistService, times(1)).addTrackToPlaylist(1L, 1L, 1);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGiveAccessToPlaylist() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(playlistController).build();

        mockMvc.perform(post("/api/playlists/{playlistId}/access", 1L)
                        .param("userId", "2"))
                .andExpect(status().isOk());

        verify(playlistService, times(1)).giveAccessToPlaylist(1L, 2L);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetUsersWithAccessToPlaylist() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(playlistController).build();
        User user = new User();
        user.setUsername("testuser");
        List<User> users = Arrays.asList(user);

        when(playlistService.getUsersWithAccessToPlaylist(anyLong())).thenReturn(users);

        mockMvc.perform(get("/api/playlists/{playlistId}/users", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testuser"));

        verify(playlistService, times(1)).getUsersWithAccessToPlaylist(1L);
    }
}
