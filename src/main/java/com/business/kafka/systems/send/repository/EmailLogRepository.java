
package com.business.kafka.systems.send.repository;

import com.business.kafka.common.entity.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {
}