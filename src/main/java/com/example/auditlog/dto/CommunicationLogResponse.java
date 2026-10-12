package com.example.auditlog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CommunicationLogResponse {

    private Long id;
    private String auditId;
    private String interactionType;
    private String notes;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate interactionDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    public CommunicationLogResponse() {
    }

    public CommunicationLogResponse(Long id, String auditId, String interactionType, String notes,
                                   LocalDate interactionDate, LocalDateTime createdAt) {
        this.id = id;
        this.auditId = auditId;
        this.interactionType = interactionType;
        this.notes = notes;
        this.interactionDate = interactionDate;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getInteractionType() {
        return interactionType;
    }

    public void setInteractionType(String interactionType) {
        this.interactionType = interactionType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getInteractionDate() {
        return interactionDate;
    }

    public void setInteractionDate(LocalDate interactionDate) {
        this.interactionDate = interactionDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}