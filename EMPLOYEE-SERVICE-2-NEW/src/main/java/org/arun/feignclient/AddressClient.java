package org.arun.feignclient;

import org.arun.response.AddressResponse;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//localhost:9091/address-app/api/address/1

@FeignClient(name = "address-service",path = "/address-app/api/")
@RibbonClient(name = "address-service")
public interface AddressClient { //proxy

//	@GetMapping("/address/{id}")
//	AddressResponse getAddressByEmployeeId(@PathVariable int id);
	
	@GetMapping("address/{employeeId}")
	public ResponseEntity<AddressResponse> getAddressByEmployeeId(@PathVariable("employeeId") int id);
}
