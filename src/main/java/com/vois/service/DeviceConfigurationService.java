package com.vois.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vois.model.Device;
import com.vois.repos.DeviceRepository;

@Service
public class DeviceConfigurationService {

	@Autowired
	DeviceRepository deviceRepository;
	
	public static final String ACTIVE = "ACTIVE";
	
	public Device configure(long deviceId) {
		Optional<Device> device = deviceRepository.findById((int) deviceId);
		if(device.isEmpty())
			return null;
		Device configuredDevice = deviceRepository.findById((int) deviceId).get();
		configuredDevice.setStatus(ACTIVE);
		configuredDevice.setTemprature(generateRandomTemprature());
		deviceRepository.saveAndFlush(configuredDevice);
		return configuredDevice;
	}
	
	public int generateRandomTemprature() {
		return new Random().nextInt(10) + 1;
	}
	
}
