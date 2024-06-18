package com.kursova.music.controller;

import com.kursova.music.model.TrackRating;
import com.kursova.music.service.TrackRatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TrackRatingControllerTest {

    @Mock
    private TrackRatingService trackRatingService;

    @InjectMocks
    private TrackRatingController trackRatingController;

    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "USER")
    public void testRateTrack() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(trackRatingController).build();
        TrackRating trackRating = new TrackRating();
        trackRating.setRating(5);

        when(trackRatingService.rateTrack(anyLong(), anyLong(), anyInt())).thenReturn(trackRating);

        mockMvc.perform(post("/api/ratings")
                        .param("trackId", "1")
                        .param("userId", "1")
                        .param("rating", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(5));

        verify(trackRatingService, times(1)).rateTrack(1L, 1L, 5);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetRatingForTrackByUser() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(trackRatingController).build();

        when(trackRatingService.getRatingForTrackByUser(anyLong(), anyLong())).thenReturn(5);

        mockMvc.perform(get("/api/ratings")
                        .param("trackId", "1")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(5));

        verify(trackRatingService, times(1)).getRatingForTrackByUser(1L, 1L);
    }

//    @Test
//    @WithMockUser(roles = "USER")
//    public void testUpdateRating() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(trackRatingController).build();
//        TrackRating trackRating = new TrackRating();
//        trackRating.setRating(4);
//
//        when(trackRatingService.updateRating(anyLong(), anyInt())).thenReturn(trackRating);
//
//        mockMvc.perform(patch("/api/ratings/{id}", 1L)
//                        .param("rating", "4"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.rating").value(4));
//
//        verify(trackRatingService, times(1)).updateRating(1L, 4);
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void testDeleteRating() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(trackRatingController).build();
//
//        mockMvc.perform(delete("/api/ratings/{id}", 1L))
//                .andExpect(status().isOk());
//
//        verify(trackRatingService, times(1)).deleteRating(1L);
//    }
}
