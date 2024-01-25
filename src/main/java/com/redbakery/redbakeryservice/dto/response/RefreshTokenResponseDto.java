package com.redbakery.redbakeryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefreshTokenResponseDto {
    private String token;
    private String accessToken;
}
