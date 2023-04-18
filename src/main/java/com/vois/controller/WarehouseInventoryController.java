package com.vois.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vois.model.Device;
import com.vois.service.WarehouseInventoryService;

@RestController
@RequestMapping("/inventory")
public class WarehouseInventoryController {

	@Autowired
	WarehouseInventoryService warehouseInventoryService;
	
	@PostMapping("/create")
	public ResponseEntity createDevice(@RequestBody Device device) {
		return ResponseEntity.ok(warehouseInventoryService.create(device));
	}
	
	@GetMapping("/devices")
	public ResponseEntity getDevices() {
		return ResponseEntity.ok(warehouseInventoryService.getAllDevices());
	}
	
	@DeleteMapping("/device/{deviceId}")
	public ResponseEntity deleteDevice(@PathVariable(value = "deviceId") long deviceId) {
		if(!warehouseInventoryService.deleteDevice(deviceId))
			return (ResponseEntity) ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.singletonMap("Status", "No Device Found by Id : " + Long.toString(deviceId)));
		return ResponseEntity.ok(Collections.singletonMap("Status", "Device Deleted Successfully"));
	}
}
