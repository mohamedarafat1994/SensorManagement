package com.vois.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vois.model.Device;

public interface DeviceRepository extends JpaRepository<Device, Integer>{

}
