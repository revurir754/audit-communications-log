package com.example.auditlog.service;

import com.example.auditlog.dto.CommunicationLogRequest;
import com.example.auditlog.model.CommunicationLog;
import com.example.auditlog.repository.CommunicationLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommunicationLogServiceTest {

    @Mock
    private CommunicationLogRepository communicationLogRepository;

    @InjectMocks
    private CommunicationLogService communicationLogService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("createCommunicationLog should save valid request")
    void createCommunicationLogShouldSaveValidRequest() {
        CommunicationLogRequest request = new CommunicationLogRequest(
                "AUD-2001",
                "PHONE",
                "Client called to confirm document delivery schedule.",
                LocalDate.now().minusDays(3)
        );

        CommunicationLog savedLog = new CommunicationLog(
                "AUD-2001",
                "PHONE",
                "Client called to confirm document delivery schedule.",
                LocalDate.now().minusDays(3)
        );
        savedLog.setId(1L);

        when(communicationLogRepository.save(any(CommunicationLog.class))).thenReturn(savedLog);

        var result = communicationLogService.createCommunicationLog(request);

        assertNotNull(result);
        assertEquals("AUD-2001", result.getAuditId());
        assertEquals("PHONE", result.getInteractionType());
    }

    @Test
    @DisplayName("createCommunicationLog should reject future dates")
    void createCommunicationLogShouldRejectFutureDate() {
        CommunicationLogRequest request = new CommunicationLogRequest(
                "AUD-2001",
                "EMAIL",
                "This note is invalid because the date is in the future.",
                LocalDate.now().plusDays(1)
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> communicationLogService.createCommunicationLog(request));

        assertTrue(exception.getMessage().contains("future"));
    }
}