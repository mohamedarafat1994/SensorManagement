package com.vois.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "device")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Device {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "temprature")
	private int temprature;
	
	@Column(name = "pin")
	private int pin;
	
}
