package com.example.auditlog.repository;

import com.example.auditlog.model.CommunicationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunicationLogRepository extends JpaRepository<CommunicationLog, Long> {
    List<CommunicationLog> findByAuditIdOrderByInteractionDateDesc(String auditId);
}