package com.kursova.music.controller;

import com.kursova.music.model.Track;
import com.kursova.music.service.TrackService;
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
public class TrackControllerTest {

    @Mock
    private TrackService trackService;

    @InjectMocks
    private TrackController trackController;

    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    public void testGetAllTracks() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(trackController).build();
        Track track = new Track();
        track.setTitle("Test Track");
        List<Track> tracks = Arrays.asList(track);

        when(trackService.getAllTracks()).thenReturn(tracks);

        mockMvc.perform(get("/api/tracks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Track"));

        verify(trackService, times(1)).getAllTracks();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testSaveTrack() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(trackController).build();
        Track track = new Track();
        track.setTitle("Test Track");

        when(trackService.saveTrack(any(Track.class))).thenReturn(track);

        mockMvc.perform(post("/api/tracks")
                        .contentType("application/json")
                        .content("{\"title\": \"Test Track\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Track"));

        verify(trackService, times(1)).saveTrack(any(Track.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetTrackById() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(trackController).build();
        Track track = new Track();
        track.setTitle("Test Track");

        when(trackService.getTrackById(anyLong())).thenReturn(track);

        mockMvc.perform(get("/api/tracks/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Track"));

        verify(trackService, times(1)).getTrackById(1L);
    }

//    @Test
//    @WithMockUser(roles = "USER")
//    public void testUpdateTrack() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(trackController).build();
//        Track track = new Track();
//        track.setTitle("Updated Track");
//
//        when(trackService.updateTrack(anyLong(), any(Track.class))).thenReturn(track);
//
//        mockMvc.perform(put("/api/tracks/{id}", 1L)
//                        .contentType("application/json")
//                        .content("{\"title\": \"Updated Track\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title").value("Updated Track"));
//
//        verify(trackService, times(1)).updateTrack(eq(1L), any(Track.class));
//    }

    @Test
    @WithMockUser(roles = "USER")
    public void testDeleteTrack() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(trackController).build();

        mockMvc.perform(delete("/api/tracks/{id}", 1L))
                .andExpect(status().isOk());

        verify(trackService, times(1)).deleteTrack(1L);
    }
}
