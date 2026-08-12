package com.thoughtprocessing.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_history")
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "status")
    private String status;

    @Column(name = "status_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime statusTime;

    @Column(name = "remarks")
    private String remarks;

    public OrderHistory() {}

    // Convenience constructor (without historyId, since it's auto-generated)
    public OrderHistory(Order order, String status, LocalDateTime statusTime, String remarks) {
        this.order = order;
        this.status = status;
        this.statusTime = statusTime;
        this.remarks = remarks;
    }

    // Getters and setters
    public Long getHistoryId() { return historyId; }
    public void setHistoryId(Long historyId) { this.historyId = historyId; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getStatusTime() { return statusTime; }
    public void setStatusTime(LocalDateTime statusTime) { this.statusTime = statusTime; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
