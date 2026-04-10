package com.thesis.alert_service.repository;

import com.thesis.alert_service.model.Alert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    Page<Alert> findAllAlertsByVesselImo(Pageable pageable, String imo);

    Page<Alert> findAllAlertsByAlertType(Pageable pageable, String alertType);
}
