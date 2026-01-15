package com.example.JWT.dto.request;

import jakarta.validation.constraints.NotBlank;

public class cancleOrderRequest {
    @NotBlank
    private String orderId;
    private String reason;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
