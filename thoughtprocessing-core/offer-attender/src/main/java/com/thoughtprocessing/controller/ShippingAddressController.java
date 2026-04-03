package com.thoughtprocessing.controller;

import com.thoughtprocessing.dto.ShippingAddressDto;
import com.thoughtprocessing.model.ShippingAddressEntity;
import com.thoughtprocessing.repository.ShippingAddressRepository;
import com.thoughtprocessing.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shipping")
@CrossOrigin("origins=http://localhost:8080")
public class ShippingAddressController {


    ShippingAddressService shippingAddressService;
    @Autowired
    private ShippingAddressRepository shippingAddressRepository;



    @Autowired
    public ShippingAddressController( ShippingAddressService shippingAddressService ) {
        this.shippingAddressService = shippingAddressService;
        this.shippingAddressRepository = shippingAddressRepository;
    }

    @PostMapping("/save")
    @ResponseBody
    ResponseEntity saveShippingAddress(@RequestBody ShippingAddressDto dto) {
        ShippingAddressDto savedDto = shippingAddressService.addShippingAddress(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }


    @GetMapping("/list")
    public List<ShippingAddressDto> findAll(@RequestParam  String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID is required");
        }
        return shippingAddressService.findAllByUserId(userId);
    }
    @GetMapping("/default/{userId}")
    public ResponseEntity<ShippingAddressEntity> getDefaultAddress(@PathVariable String userId) {
        Optional<ShippingAddressEntity> address = shippingAddressRepository.findDefaultAddress(userId);
        return shippingAddressRepository.findDefaultAddress(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());


    }
   /* @PutMapping("/update/{userId}")
    public ResponseEntity<ShippingAddressDto> updateProfile(
            @PathVariable String userId,
            @RequestBody ShippingAddressDto dto) {
        dto.setUserId(userId); // ensure the ID is set
        ShippingAddressDto updatedDto = shippingAddressService.updateShippingAddress(dto);
        if (updatedDto != null) {
            return ResponseEntity.ok(updatedDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

*/


    /*@GetMapping("/default/{userId}")
    public ResponseEntity<List<ShippingAddressEntity>> findAllByUserId(@PathVariable String userId) {
        List<ShippingAddressEntity> addresses = shippingAddressRepository.findAllByUserId(userId);
        if (addresses.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(addresses);
        }
    }*/





}
