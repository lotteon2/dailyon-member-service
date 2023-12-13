package com.dailyon.memeberservice.address.repository;

import com.dailyon.memeberservice.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dailyon.memeberservice.address.repository.custom.AddressRepositoryCustom;

public interface AddressRepository extends JpaRepository<Address, Long>, AddressRepositoryCustom {
}
