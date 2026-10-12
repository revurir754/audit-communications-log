package com.example.auditlog.service;

import com.example.auditlog.dto.CommunicationLogRequest;
import com.example.auditlog.dto.CommunicationLogResponse;
import com.example.auditlog.model.CommunicationLog;
import com.example.auditlog.repository.CommunicationLogRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommunicationLogService {

    private final CommunicationLogRepository communicationLogRepository;

    public CommunicationLogService(CommunicationLogRepository communicationLogRepository) {
        this.communicationLogRepository = communicationLogRepository;
    }

    @Transactional
    public CommunicationLogResponse createCommunicationLog(CommunicationLogRequest request) {
        validateRequest(request);

        CommunicationLog log = new CommunicationLog(
                request.getAuditId(),
                request.getInteractionType(),
                request.getNotes(),
                request.getInteractionDate()
        );

        CommunicationLog savedLog = communicationLogRepository.save(log);
        return toResponse(savedLog);
    }

    @Transactional(readOnly = true)
    public List<CommunicationLogResponse> getLogsByAuditId(String auditId) {
        if (auditId == null || auditId.isBlank()) {
            throw new IllegalArgumentException("Audit ID is required");
        }

        return communicationLogRepository.findByAuditIdOrderByInteractionDateDesc(auditId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CommunicationLogResponse getLogById(Long id) {
        CommunicationLog log = communicationLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Communication log not found with id: " + id));
        return toResponse(log);
    }

    private void validateRequest(CommunicationLogRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request body is required");
        }

        if (request.getAuditId() == null || request.getAuditId().isBlank()) {
            throw new IllegalArgumentException("Audit ID is required");
        }

        if (request.getInteractionType() == null || request.getInteractionType().isBlank()) {
            throw new IllegalArgumentException("Interaction type is required");
        }

        if (request.getNotes() == null || request.getNotes().isBlank()) {
            throw new IllegalArgumentException("Notes are required");
        }

        if (request.getInteractionDate() == null) {
            throw new IllegalArgumentException("Interaction date is required");
        }

        if (request.getInteractionDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Interaction date cannot be in the future");
        }
    }

    private CommunicationLogResponse toResponse(CommunicationLog log) {
        return new CommunicationLogResponse(
                log.getId(),
                log.getAuditId(),
                log.getInteractionType(),
                log.getNotes(),
                log.getInteractionDate(),
                log.getCreatedAt()
        );
    }
}