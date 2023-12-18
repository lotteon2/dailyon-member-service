package com.dailyon.memeberservice.address.api;

import com.dailyon.memeberservice.address.api.response.AddressGetRequest;
import com.dailyon.memeberservice.address.entity.Address;
import com.dailyon.memeberservice.address.api.request.AddressCreateRequest;
import com.dailyon.memeberservice.address.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@CrossOrigin(origins = "*")
public class AddressApiController {
    private final AddressService addressService;

    public AddressApiController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("")
    public ResponseEntity<Long> createMemberAddress(@RequestBody AddressCreateRequest request, @RequestHeader Long memberId ){
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAddress(request, memberId));
    }

    @GetMapping
    public ResponseEntity<ResponseEntity<List<AddressGetRequest>>> getMemberAddress(@RequestHeader Long memberId ){
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.getMemberAddress(memberId));
    }
}
