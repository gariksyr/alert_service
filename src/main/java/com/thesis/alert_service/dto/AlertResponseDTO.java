package com.thesis.alert_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlertResponseDTO {
    private Long id;
    private String vesselImo;
    private String alertType;
    private String message;
    private LocalDateTime timestamp;
}
