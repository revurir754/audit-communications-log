package com.example.auditlog.controller;

import com.example.auditlog.dto.CommunicationLogRequest;
import com.example.auditlog.dto.CommunicationLogResponse;
import com.example.auditlog.service.CommunicationLogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommunicationLogController {

    private final CommunicationLogService communicationLogService;

    public CommunicationLogController(CommunicationLogService communicationLogService) {
        this.communicationLogService = communicationLogService;
    }

    @PostMapping("/communication-logs")
    public ResponseEntity<CommunicationLogResponse> createCommunicationLog(
            @Valid @RequestBody CommunicationLogRequest request) {

        CommunicationLogResponse response = communicationLogService.createCommunicationLog(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/communication-logs")
    public ResponseEntity<List<CommunicationLogResponse>> getLogsByAuditId(
            @RequestParam String auditId) {

        List<CommunicationLogResponse> response = communicationLogService.getLogsByAuditId(auditId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/communication-logs/{id}")
    public ResponseEntity<CommunicationLogResponse> getLogById(@PathVariable Long id) {
        CommunicationLogResponse response = communicationLogService.getLogById(id);
        return ResponseEntity.ok(response);
    }
}