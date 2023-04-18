package com.vois.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vois.model.Device;
import com.vois.service.DeviceConfigurationService;

@RestController
@RequestMapping("/configuration")
public class DeviceConfigurationController {

	@Autowired
	DeviceConfigurationService deviceConfigurationService;
	
	@PostMapping("/configure/{deviceId}")
	public ResponseEntity configure(@PathVariable(value = "deviceId") long deviceId) {
		Device device = deviceConfigurationService.configure(deviceId);
		if (device != null)
			return ResponseEntity.ok(device);
		return (ResponseEntity) ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.singletonMap("Status", "No Device Found by Id : " + Long.toString(deviceId)));
	}
	
}
