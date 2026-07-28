package com.thoughtprocessing.controller;

import com.thoughtprocessing.model.Order;
import com.thoughtprocessing.model.OrderEntity;
import com.thoughtprocessing.model.OrderItemEntity;
import com.thoughtprocessing.service.ItemsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@CrossOrigin("origins=http://localhost:8080")
public class ItemsController {

    private final ItemsService itemsService;

    public ItemsController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<OrderItemEntity> addItem(
            @PathVariable String orderId,
            @RequestBody OrderItemEntity item) {
        return ResponseEntity.ok(itemsService.addItemToOrder(orderId, item));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<List<OrderItemEntity>> getItems(@PathVariable String orderId) {
        return ResponseEntity.ok(itemsService.getItemsByOrderId(orderId));
    }

    @GetMapping("/details/{orderId}")
    public ResponseEntity<Order> getOrderWithItems(@PathVariable String orderId) {
        return itemsService.getOrderWithItems(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<OrderItemEntity> updateItem(@RequestBody OrderItemEntity item) {
        return ResponseEntity.ok(itemsService.updateItem(item));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long itemId) {
        itemsService.removeItem(itemId);
        return ResponseEntity.noContent().build();
    }
}

