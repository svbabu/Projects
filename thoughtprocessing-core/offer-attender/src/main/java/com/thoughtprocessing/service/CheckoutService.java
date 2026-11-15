package com.thoughtprocessing.service;

import com.thoughtprocessing.dto.*;
import com.thoughtprocessing.model.*;
import com.thoughtprocessing.repository.*;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

import static org.json.XMLTokener.entity;

@Service
public class CheckoutService {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutService.class);

    //@Autowired
    //private ShippingAddressService shippingAddressService;
    private final ShippingAddressService shippingAddressService;


    @Autowired
    private PaymentService paymentService;  // ✅ inject the service bean

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    /*public CheckoutService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }*/
    public CheckoutService(ShippingAddressService shippingAddressService) {
        this.shippingAddressService = shippingAddressService;
    }

    public ShippingAddressEntity getDefaultShippingAddress(String userId) {
        return shippingAddressService.getDefaultAddress(userId);
    }
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO request) throws Exception
    {

        Order order = toOrder(request);
        // Generate internal order id if needed
        order.setOrderId(UUID.randomUUID().toString());


        // Step 3: Snapshot shipping address BEFORE saving order

        if (request.getShippingAddress() != null) {
            ShippingAddressDto addrDto = request.getShippingAddress();

            //ShippingAddressEntity snapshot; // declare once
           ShippingAddressEntity   snapshot = addrDto.isUseDefault()
                    // ShippingAddressEntity   snapshot = useDefaultAddress
                   ? shippingAddressService.cloneDefaultAddress(addrDto.getUserId())
                     : shippingAddressService.addShippingAddressEntity(addrDto);


            // Attach the snapshot entity to the order
            order.setShippingAddress(snapshot);
           // }
        }


        // Step 4: Attach items
        /*List<OrderItemEntity> existingItems = orderItemRepository.findByOrderOrderId(order.getOrderId());
        if (existingItems.isEmpty() && request.getItems() != null) {
            for (OrderItemDTO dto : request.getItems()) {
                order.addItem(toItemEntity(dto, order));
            }
        }*/
        // Add items
        if (request.getItems() != null) {
            for (OrderItemDTO dto : request.getItems()) {
                order.addItem(toItemEntity(dto, order));
            }
        }

        // Step 5: Save order
        Order savedOrder = orderRepository.save(order);

        // Step 6: Re‑attach Payment
       // payment.setOrder(savedOrder);
        //paymentRepository.save(payment);

        // Step 7: Build response
        OrderResponseDTO dto= new OrderResponseDTO() ;
       // dto= toResponseDTO(savedOrder, payment);
       // if (!"cod".equals(method)) {
            //dto.setRazorpayOrderId(order.getOrderId());
        //}
        dto.setOrderId(savedOrder.getOrderId());
        dto.setCustomerId(savedOrder.getCustomerId());
        dto.setOrderStatus(savedOrder.getOrderStatus());
        dto.setTotalAmount(savedOrder.getTotalAmount());
        return dto;
    }

    private String generateInternalOrderId() {
        // Example: COD-20260717-0001
        String datePart = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE); // e.g. 20260717
        String randomPart = String.format("%04d", new Random().nextInt(10000));     // 0000–9999
        return "COD-" + datePart + "-" + randomPart;
    }


   // }

    private OrderResponseDTO toResponseDTO(Order order, Payment payment) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(order.getOrderId());  // internal backend ID
        dto.setCustomerId(order.getCustomerId());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        dto.setTotalAmount(order.getTotalAmount());

        // Items
        dto.setItems(order.getItems().stream()
                .map(this::toItemDTO)   // implement toItemDTO to convert OrderItemEntity → OrderItemDTO
                .collect(Collectors.toList()));

        // Shipping
        if (order.getShippingAddress() != null) {
            ShippingAddressEntity addr = order.getShippingAddress();

            StringBuilder sb = new StringBuilder();

            if (addr.getFullName() != null) sb.append(addr.getFullName());
            if (addr.getBuildingName() != null) sb.append(", ").append(addr.getBuildingName());
            if (addr.getStreetName() != null) sb.append(", ").append(addr.getStreetName());
            if (addr.getCity() != null) sb.append(", ").append(addr.getCity());
            if (addr.getState() != null) sb.append(", ").append(addr.getState());
            if (addr.getPincode() != null) sb.append(" - ").append(addr.getPincode());
            dto.setRecipientName(addr.getFullName());
            dto.setMobile(addr.getMobileNumber());

            dto.setAddress(sb.toString());


        }

        // Payment summary + details
        if (payment != null) {
            dto.setPaymentMethod(payment.getMethod());
            dto.setReceipt(order.getReceipt());
            dto.setPaymentDetails(toOrderSuccessDTO(payment)); // implement this to map Payment → OrderSuccessDTO
        }

        return dto;
    }
    private Order toOrder(OrderRequestDTO request) {
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setOrderStatus("CREATED");
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalAmount(request.getTotalAmount());
        order.setAttempts(0); // initialize attempts

        // ⚠️ Optional: attach items here, but safer to do it later in createOrder


        return order;
    }

    private OrderItemEntity toItemEntity(OrderItemDTO dto, Order order)  {
        System.out.println(
                "Model Name = " +
                        dto.getModelName());

        System.out.println(
                "Image Url = " +
                        dto.getImageUrl());

        OrderItemEntity entity = new OrderItemEntity();
        entity.setProductId(dto.getProductId());
        entity.setProductName(dto.getProductName());
        entity.setModelName(dto.getModelName());
        entity.setDescription(dto.getDescription());
        entity.setImageUrl(dto.getImageUrl());
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getAppliedPrice());  //updated setprice
        entity.setBasePrice(dto.getBasePrice());
        entity.setAppliedPrice(dto.getAppliedPrice());
        entity.setDiscountPercentage(dto.getDiscountPercentage());
        entity.setOfferId(dto.getOfferId());

        // link back to parent order
        entity.setOrder(order);

        return entity;
    }


    private OrderItemDTO toItemDTO(OrderItemEntity entity) {

        OrderItemDTO dto = new OrderItemDTO();

        dto.setProductId(entity.getProductId());
        dto.setProductName(entity.getProductName());
        dto.setModelName(entity.getModelName());
        dto.setDescription(entity.getDescription());
        System.out.println(
                "Order Item Image = " +
                        dto.getImageUrl()
        );
        dto.setImageUrl(entity.getImageUrl());
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getAppliedPrice());
        dto.setBasePrice(entity.getBasePrice());
        dto.setAppliedPrice(entity.getAppliedPrice());
        dto.setDiscountPercentage(entity.getDiscountPercentage());
        dto.setOfferId(entity.getOfferId());
        return dto;
    }
    private OrderSuccessDTO toOrderSuccessDTO(Payment payment) {
        OrderSuccessDTO dto = new OrderSuccessDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setMethod(payment.getMethod());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setUpiId(payment.getUpiId());
        dto.setTransactionId(payment.getTransactionId());
        return dto;
    }
    private ShippingAddressEntity toAddressEntity(ShippingAddressDto shippingAddress) {
        ShippingAddressEntity entity = new ShippingAddressEntity();
        entity.setUserId(String.valueOf(shippingAddress.getUserId()));
        entity.setFullName(shippingAddress.getFullName());
        entity.setBuildingName(shippingAddress.getBuildingName());
        entity.setStreetName(shippingAddress.getStreetName());
        entity.setCity(shippingAddress.getCity());
        entity.setState(shippingAddress.getState());
        entity.setPincode(shippingAddress.getPincode());
        entity.setMobileNumber(shippingAddress.getMobileNumber());
        entity.setLandmark(shippingAddress.getLandmark());
        entity.setAddressType(shippingAddress.getAddressType());
        entity.setUseDefault(false);
        logger.info("Incoming ShippingAddressDto: {}", shippingAddress);
        return entity;
    }
    @Transactional(readOnly = true)
    public OrderResponseDTO getFullOrderDetails(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

       // Payment payment = paymentRepository.findByOrderId(orderId);
        Payment payment = paymentRepository.findByOrder_OrderId(orderId);
        if (payment == null) {
            throw new RuntimeException("Payment not found: " + orderId);
        }



        return toResponseDTO(order, payment);
    }


}
