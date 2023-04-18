package com.vois.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vois.model.Device;
import com.vois.repos.DeviceRepository;

@Service
public class WarehouseInventoryService {

	@Autowired
	DeviceRepository deviceRepository;
	
	private static final Logger LOG = LoggerFactory.getLogger(WarehouseInventoryService.class);
	private static final int DIGITS = 7;
	
	public Device create(Device device) {
		device.setPin(generatePIN());
		return deviceRepository.save(device);
	}


	public List<Device> getAllDevices() {
		return deviceRepository.findAll();
	}


	public boolean deleteDevice(long deviceId) {
		Optional<Device> device = deviceRepository.findById((int) deviceId);
		if(device.isEmpty())
			return false;
		deviceRepository.delete(device.get());
		return true;
	}
	
	public static int generatePIN() {
	    int m = (int) Math.pow(10, DIGITS - 1);
	    return m + new Random().nextInt(DIGITS * m);
	}
	
}
