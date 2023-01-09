package org.arun.service;

import java.util.Arrays;
import java.util.List;

import org.arun.entity.Address;
import org.arun.repo.AddressRepo;
import org.arun.response.AddressResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

	@Autowired
	private AddressRepo addressRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public AddressResponse findAddressByEmployeeId(int employeeId) {
		
		System.out.println("Finding Address for Employee "+employeeId);
		
		Address address = addressRepo.findAddressByEmployeeId(employeeId);
		AddressResponse addressResponse = modelMapper.map(address, AddressResponse.class);
		
		return addressResponse;
		
	}

	public List<AddressResponse> getAllAddress() {
		
		List<Address> allAddress = addressRepo.findAll();
		
		List<AddressResponse> addressList = Arrays.asList(modelMapper.map(allAddress, AddressResponse[].class));
		
		return addressList;
	}
}
