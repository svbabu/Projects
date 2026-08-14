package com.thoughtprocessing.dto;

import com.thoughtprocessing.model.Order;
import com.thoughtprocessing.model.OrderHistory;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class OrderHistoryDto {

    @NotNull
    private Long historyId;
    @NotNull
    private String orderId;   // use orderId instead of full Order
    @NotBlank
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotNull
    private LocalDateTime statusTime;
    private String remarks;

    private OrderDto order;
    public OrderHistoryDto() {}

    // Convenience constructor (without historyId, since it's auto-generated)
    public OrderHistoryDto(Long historyId, String orderId, String status, LocalDateTime statusTime, String remarks) {
        this.historyId = historyId;
        this.orderId = orderId;
        this.status = status;
        this.statusTime = statusTime;
        this.remarks = remarks;
    }

    // Getters and setters
    public Long getHistoryId() { return historyId; }
    public void setHistoryId(Long historyId) { this.historyId = historyId; }

    public OrderDto getOrder() { return order; }
    public void setOrder(OrderDto order) { this.order = order; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getStatusTime() { return statusTime; }
    public void setStatusTime(LocalDateTime statusTime) { this.statusTime = statusTime; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public static OrderHistoryDto fromEntity(OrderHistory history) {
        OrderHistoryDto dto = new OrderHistoryDto();
        dto.setHistoryId(history.getHistoryId());
        dto.setOrderId(history.getOrder().getOrderId());
        dto.setStatus(history.getStatus());
        dto.setStatusTime(history.getStatusTime());
        dto.setRemarks(history.getRemarks());
        return dto;
    }

}


