package com.thesis.alert_service.kafka;

import com.thesis.alert_service.dto.MeasurementEvent;
import com.thesis.alert_service.model.Alert;
import com.thesis.alert_service.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertConsumer {

    private final AlertRepository alertRepository;
    private static final double SPEED_LIMIT = 25.0;

    @KafkaListener(topics = "telemetry-topic", groupId = "alert-group")
    public void consume(MeasurementEvent event) {
        log.info("Received telemetry for vessel: {}", event.getVesselImo());

        if (event.getSpeed() > SPEED_LIMIT) {
            Alert alert = new Alert();
            alert.setVesselImo(event.getVesselImo());
            alert.setAlertType("OVER_SPEED");
            alert.setMessage("Vessel exceeded speed limit: " + event.getSpeed() + " knots");
            alert.setTimestamp(LocalDateTime.now());

            alertRepository.save(alert);
            log.warn("ALERT CREATED: Vessel {} is going too fast!", event.getVesselImo());
        }
    }
}
