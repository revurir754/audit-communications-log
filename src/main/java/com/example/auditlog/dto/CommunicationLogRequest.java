package com.example.auditlog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CommunicationLogRequest {

    @NotBlank(message = "Audit ID is required")
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "Audit ID can only contain letters, numbers, and hyphens")
    private String auditId;

    @NotBlank(message = "Interaction type is required")
    @Pattern(regexp = "^(EMAIL|PHONE|MEETING|NOTE|REPORT|OTHER)$", message = "Interaction type must be one of EMAIL, PHONE, MEETING, NOTE, REPORT, OTHER")
    private String interactionType;

    @NotBlank(message = "Notes are required")
    @Size(min = 5, max = 2000, message = "Notes must be between 5 and 2000 characters")
    private String notes;

    @NotNull(message = "Interaction date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate interactionDate;

    public CommunicationLogRequest() {
    }

    public CommunicationLogRequest(String auditId, String interactionType, String notes, LocalDate interactionDate) {
        this.auditId = auditId;
        this.interactionType = interactionType;
        this.notes = notes;
        this.interactionDate = interactionDate;
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
}