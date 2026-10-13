package com.example.auditlog.controller;

import com.example.auditlog.dto.CommunicationLogRequest;
import com.example.auditlog.service.CommunicationLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommunicationLogController.class)
@WithMockUser
class CommunicationLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommunicationLogService communicationLogService;

    @Test
    @DisplayName("POST /api/communication-logs should create a log")
    void createCommunicationLogShouldReturnCreated() throws Exception {
        CommunicationLogRequest request = new CommunicationLogRequest(
                "AUD-1001",
                "EMAIL",
                "Follow-up email sent to client regarding tax schedule.",
                LocalDate.now().minusDays(2)
        );

        var response = new com.example.auditlog.dto.CommunicationLogResponse(
                1L,
                "AUD-1001",
                "EMAIL",
                "Follow-up email sent to client regarding tax schedule.",
                LocalDate.now().minusDays(2),
                java.time.LocalDateTime.now()
        );

        when(communicationLogService.createCommunicationLog(any(CommunicationLogRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/communication-logs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.auditId").value("AUD-1001"))
                .andExpect(jsonPath("$.interactionType").value("EMAIL"));
    }

    @Test
    @DisplayName("GET /api/communication-logs should return logs for an audit ID")
    void getLogsByAuditIdShouldReturnList() throws Exception {
        var response = new com.example.auditlog.dto.CommunicationLogResponse(
                1L,
                "AUD-1001",
                "MEETING",
                "Discussed documentation follow-up.",
                LocalDate.now().minusDays(1),
                java.time.LocalDateTime.now()
        );

        when(communicationLogService.getLogsByAuditId("AUD-1001")).thenReturn(java.util.List.of(response));

        mockMvc.perform(get("/api/communication-logs").param("auditId", "AUD-1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].auditId").value("AUD-1001"));
    }
}
