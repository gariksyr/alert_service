package com.thesis.alert_service.dto;

import lombok.Data;

@Data
public class AlertRequestDTO {
    private String vesselImo;
    private String alertType;
    private String message;
}
