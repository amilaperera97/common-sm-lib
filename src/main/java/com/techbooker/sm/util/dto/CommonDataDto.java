package com.techbooker.sm.util.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonDataDto {
    private UserProfileDto user;
}
