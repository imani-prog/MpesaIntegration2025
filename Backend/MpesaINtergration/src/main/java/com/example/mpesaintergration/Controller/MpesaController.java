package com.example.mpesaintergration.Controller;

import com.example.mpesaintergration.DTO.StkPushRequest;
import com.example.mpesaintergration.DTO.StkPushResponse;
import com.example.mpesaintergration.service.MpesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mpesa")
@RequiredArgsConstructor
public class MpesaController {

    private final MpesaService mpesaService;

    @PostMapping("/pay")
    public ResponseEntity<StkPushResponse> pay(@RequestBody StkPushRequest request) {
        String result = mpesaService.stkPush(request.getPhone(), request.getAmount());
        return ResponseEntity.ok(new StkPushResponse(true, result));
    }
}
