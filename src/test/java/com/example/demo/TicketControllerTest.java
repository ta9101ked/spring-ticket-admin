package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketRepository ticketRepository;

    @BeforeEach
    void setUp() {
        ticketRepository.deleteAll();
    }

    @Test
    void list_returnsIndexView() throws Exception {
        mockMvc.perform(get("/tickets"))
                .andExpect(status().isOk())
                .andExpect(view().name("tickets/index"))
                .andExpect(model().attributeExists("tickets"));
    }

    @Test
    void create_withInvalidInput_returnsForm() throws Exception {
        mockMvc.perform(post("/tickets")
                        .param("ticketNo", "")
                        .param("title", "")
                        .param("status", "")
                        .param("priority", "")
                        .param("requesterName", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("tickets/new"));
    }

    @Test
    void create_withValidInput_redirectsToList() throws Exception {
        mockMvc.perform(post("/tickets")
                        .param("ticketNo", "T-MVC-1")
                        .param("title", "Created via test")
                        .param("status", TicketStatus.OPEN)
                        .param("priority", TicketPriority.HIGH)
                        .param("requesterName", "Tester"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tickets"));
    }

    @Test
    void edit_missingId_returnsNotFoundView() throws Exception {
        mockMvc.perform(get("/tickets/999/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/not-found"));
    }
}
