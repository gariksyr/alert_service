package com.thesis.alert_service.service;

import com.thesis.alert_service.dto.AlertResponseDTO;
import com.thesis.alert_service.exception.EntityNotFoundException;
import com.thesis.alert_service.model.Alert;
import com.thesis.alert_service.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlertService {
    private final AlertRepository alertRepository;
    private final ModelMapper modelMapper;

    public Page<AlertResponseDTO> findAllAlerts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return alertRepository.findAll(pageable).map(src -> modelMapper.map(src, AlertResponseDTO.class)) ;
    }
    public Page<AlertResponseDTO> findAllAlertsByImo(String imo, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return alertRepository.findAllAlertsByVesselImo(pageable,imo).map(src -> modelMapper.map(src, AlertResponseDTO.class));
    }
    public Page<AlertResponseDTO> findAllAlertsByType(String type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return alertRepository.findAllAlertsByAlertType(pageable,type).map(src -> modelMapper.map(src, AlertResponseDTO.class));
    }
    @Transactional
    public void deleteAlertById(@PathVariable Long id) {
        Alert alert = alertRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        alertRepository.delete(alert);
    }
    @Transactional
    public void saveAlert(Alert alert) {
        alertRepository.save(alert);
    }
}
