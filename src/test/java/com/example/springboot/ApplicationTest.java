package com.example.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTest {
    @Autowired
    private MockMvc mockMvc;

    // Recitation Examples

    @Test
    void testHistory() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=testing")).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.get("/history").contentType(MediaType.ALL))
                .andExpect(content().string(containsString("testing")));
    }

    @Test
    void testDelete() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=stringToDelete")).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/delete?post_text=stringToDelete").contentType(MediaType.ALL))
                .andExpect(content().string(containsString("has been deleted")));
        mockMvc.perform(MockMvcRequestBuilders.post("/delete?post_text=stringToDelete").contentType(MediaType.ALL))
                .andExpect(content().string(containsString("does not exist")));
    }

    // Recitation Example but my version does not have a search
    //

//    @Test
//    void testSearch() throws Exception{
//        mockMvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=toBeSearched")).andReturn();
//        mockMvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=notToBeSearched")).andReturn();
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/history").contentType(MediaType.ALL))
//                .andExpect(content().string(containsString("toBeSearched")));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/history").contentType(MediaType.ALL))
//                .andExpect(content().string(containsString("notToBeSearched")));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/search?search_text=toBeSearched").contentType(MediaType.ALL))
//                .andExpect(content().string(containsString("toBeSearched")));
//        mockMvc.perform(MockMvcRequestBuilders.get("/search?search_text=toBeSearched").contentType(MediaType.ALL))
//                .andExpect(content().string(not(containsString("notToBeSearched"))));
//    }

    // Homework Tests

    @Test
    void testPostDelete() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=string")).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/delete?post_text=string"))
                .andExpect(content().string(containsString("has been deleted")));
        mockMvc.perform(MockMvcRequestBuilders.get("/history").contentType(MediaType.ALL))
                .andExpect(content().string(not(containsString("string"))));
    }

    @Test
    void caseSensitiveDelete() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api?post_input_text=stringToDelete")).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/delete?post_text=STRINGTODELETE").contentType(MediaType.ALL))
                .andExpect(content().string(containsString("does not exist")));
    }
}