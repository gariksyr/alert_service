package com.thesis.alert_service.service;

import com.thesis.alert_service.exception.EntityNotFoundException;
import com.thesis.alert_service.model.Zone;
import com.thesis.alert_service.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ZoneService {
    private final ZoneRepository zoneRepository;
    @Transactional
    public void saveZone(Zone zone) {
        zoneRepository.save(zone);
    }
    public List<Zone> findZonesContainingPoint(double lat, double lon) {
        return zoneRepository.findZonesContainingPoint(lat, lon);
    }
    @Transactional
    public void deleteZone(Long id) {
        Zone zone = zoneRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        zoneRepository.delete(zone);
    }
}
