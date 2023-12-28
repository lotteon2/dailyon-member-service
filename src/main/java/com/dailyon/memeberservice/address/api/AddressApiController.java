package com.dailyon.memeberservice.address.api;

import com.dailyon.memeberservice.address.api.response.AddressGetResponse;
import com.dailyon.memeberservice.address.api.request.AddressCreateRequest;
import com.dailyon.memeberservice.address.service.AddressService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("")
    public ResponseEntity<Page<AddressGetResponse>> getMemberAddress(@RequestHeader Long memberId, Pageable pageable ){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.getMemberAddress(memberId, pageable));
    }

    @PostMapping("/default")
    public ResponseEntity<Long> setDefaultAddress(@RequestHeader Long memberId,@RequestBody Long addressId ){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.setDefaultAddress(memberId, addressId));
    }

    @GetMapping("/default")
    public ResponseEntity<AddressGetResponse> getDefaultAddress(@RequestHeader Long memberId ){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.getDefaultAddress(memberId));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity deleteAddress(@RequestHeader Long memberId, @PathVariable Long addressId ){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.deleteAddress(memberId, addressId));
    }
}
