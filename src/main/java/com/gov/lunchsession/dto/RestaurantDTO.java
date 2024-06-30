package com.gov.lunchsession.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantDTO {
    private Long id;
    private String name;
    private Long session_id;
}
