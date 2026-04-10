package com.thesis.alert_service.kafka;

import com.thesis.alert_service.dto.MeasurementEvent;
import com.thesis.alert_service.model.Alert;
import com.thesis.alert_service.model.Zone;
import com.thesis.alert_service.repository.AlertRepository;
import com.thesis.alert_service.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertConsumer {

    private final AlertRepository alertRepository;
    private final ZoneRepository zoneRepository;
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
        List<Zone> zones = zoneRepository.findZonesContainingPoint(event.getLatitude(), event.getLongitude());

        for (Zone zone : zones) {
            Alert alert = new Alert();
            alert.setVesselImo(event.getVesselImo());
            alert.setAlertType("ZONE_VIOLATION");
            alert.setMessage("Vessel come to forbidden zone: " + zone.getName());
            alert.setTimestamp(LocalDateTime.now());
            alertRepository.save(alert);
            log.warn("WARNING: Vessel {} in the zone {}!", event.getVesselImo(), zone.getName());
    }
}
}
