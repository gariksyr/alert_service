package com.thesis.alert_service.controller;

import com.thesis.alert_service.dto.AlertResponseDTO;
import com.thesis.alert_service.model.Zone;
import com.thesis.alert_service.service.AlertService;
import com.thesis.alert_service.service.ZoneService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Alert management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alerts")
public class AlertController {
    private final AlertService alertService;
    private final ZoneService zoneService;
    @GetMapping
    public ResponseEntity<Page<AlertResponseDTO>> findAllAlerts(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "100") int size) {
        return ResponseEntity.ok(alertService.findAllAlerts(page, size));
    }
    @GetMapping("/vessel/{imo}")
    public ResponseEntity<Page<AlertResponseDTO>> findAllAlertsByImo(@PathVariable String imo,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "100") int size) {
        return ResponseEntity.ok(alertService.findAllAlertsByImo(imo, page, size));
    }
    @GetMapping("/type/{type}")
    public ResponseEntity<Page<AlertResponseDTO>> findAllAlertsByType(@PathVariable String type,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "100") int size) {
        return ResponseEntity.ok(alertService.findAllAlertsByType(type, page, size));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long id) {
        alertService.deleteAlertById(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/zone")
    public ResponseEntity<Void> saveZone(@RequestBody Zone zone) {
        zoneService.saveZone(zone);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/zone/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable Long id) {
        zoneService.deleteZone(id);
        return ResponseEntity.ok().build();
    }
}
