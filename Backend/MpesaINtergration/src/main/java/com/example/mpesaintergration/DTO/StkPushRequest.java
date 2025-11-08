package com.example.mpesaintergration.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StkPushRequest {
    private String phone;
    private String amount;
}
