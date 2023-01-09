package org.arun.service;

import org.arun.entity.Employee;
import org.arun.feignclient.AddressClient;
import org.arun.repo.EmployeeRepo;
import org.arun.response.AddressResponse;
import org.arun.response.EmployeeResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private WebClient webClient;
	
	@Autowired
	private AddressClient addressClient;

	public EmployeeResponse getEmployeeById(@PathVariable int id) {

		// Employee -> Employeeresponse
		Employee employee = employeeRepo.findById(id).get(); // db call -> 10
		
		EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);

		//10s
		ResponseEntity<AddressResponse> addressResponseEntity =addressClient.getAddressByEmployeeId(id);
		AddressResponse addressResponse = addressResponseEntity.getBody();

		employeeResponse.setAddressResponse(addressResponse);
		return employeeResponse;

	}

}
