package com.thoughtprocessing.dto;

import java.time.LocalDateTime;

public class CourierEventPayloadDto {
    private String orderId;
    private String status;
    private LocalDateTime time;
    private String remarks;
    public CourierEventPayloadDto() {}
    public CourierEventPayloadDto(String orderId, String status, LocalDateTime time, String remarks) {
        this.orderId = orderId;
        this.status = status;
        this.time = time;
        this.remarks = remarks;

    }
    public String getOrderId() {
        return orderId;

    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getTime() {
        return time;

    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
