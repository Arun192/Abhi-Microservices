package org.arun.controller;

import java.util.List;

import org.arun.response.AddressResponse;
import org.arun.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	@GetMapping("/address")
	public ResponseEntity<List<AddressResponse>> getAllAddress(){
		
		List<AddressResponse> addressResponse = addressService.getAllAddress();
		
		return ResponseEntity.status(HttpStatus.OK).body(addressResponse);
	}

	@GetMapping("address/{employeeId}")
	public ResponseEntity<AddressResponse> getAddressByEmployeeId(@PathVariable("employeeId") int id) {

		AddressResponse addressResponse = null;

		addressResponse = addressService.findAddressByEmployeeId(id);

		return ResponseEntity.status(HttpStatus.OK).body(addressResponse);

	}
}
