package com.mayavan.fuelman.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mayavan.fuelman.repo.model.VehicleOwner;

public interface VehicleOwnerRepository extends JpaRepository<VehicleOwner, Integer>{

}
