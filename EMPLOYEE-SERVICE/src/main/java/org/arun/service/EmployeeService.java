package org.arun.service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.arun.entity.Employee;
import org.arun.openfeignclients.AddressClient;
import org.arun.repo.EmployeeRepo;
import org.arun.response.AddressResponse;
import org.arun.response.EmployeeResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
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
	private RestTemplate restTemplate;

	@Autowired
	private AddressClient addressClient;

//	@Autowired
//	private DiscoveryClient discoveryClient;

//	@Autowired
//	private LoadBalancerClient loadBalancerClient;

//	@Value("${addressservice.base.url}")
//	private String addressBaseURL;

//	public EmployeeService(@Value("${addressservice.base.url}") String addressBaseURL,RestTemplateBuilder builder) {
//		
//		//System.out.println(" URI "+ addressBaseURL);
//		this.restTemplate = builder
//							.rootUri(addressBaseURL)
//							.build();
//	}
//	

	public EmployeeResponse getEmployeeById(@PathVariable int id) {

		// set a data by making Rest API call
		// AddressResponse addressResponse = new AddressResponse();

		// Employee --> EmployeeResponse
		Employee employee = employeeRepo.findById(id).get();
		EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);

//		AddressResponse	addressResponse = restTemplate.getForObject("/address/{id}",
//				AddressResponse.class, id);

		// AddressResponse addressResponse = callingAddressServiceUsingRESTTempate(id);

		ResponseEntity<AddressResponse> addressResponseEntity = addressClient.getAddressByEmployeeId(id);
		AddressResponse addressResponse = addressResponseEntity.getBody();

		employeeResponse.setAddressResponse(addressResponse);

//		EmployeeResponse employeeResponse = new EmployeeResponse();
//		employeeResponse.setId(employee.getId());
//		employeeResponse.setName(employee.getName());
//		employeeResponse.setEmail(employee.getEmail());
//		employeeResponse.setBloodGroup(employee.getBloodGroup());

		return employeeResponse;

	}

	private AddressResponse callToAddressServiceUsingWebClient(int id) {
		return webClient.get().uri("/address/" + id).retrieve().bodyToMono(AddressResponse.class).block();

	}

	// @SuppressWarnings("unused")
	private AddressResponse callingAddressServiceUsingRESTTempate(int id) {

		// gettin the details for the IP and a port number for address service

//		List<ServiceInstance> instances = discoveryClient.getInstances("address-service");
//		ServiceInstance serviceInstance = instances.get(0);
//		String uri = serviceInstance.getUri().toString();

//		ServiceInstance serviceInstance = loadBalancerClient.choose("address-service");
//		String uri = serviceInstance.getUri().toString();
//		String contextRoot = serviceInstance.getMetadata().get("configPath");
//		System.out.println(serviceInstance.getMetadata().get("user"));
//		System.out.println(serviceInstance.getMetadata().get("password"));

		// System.out.println("uri >>>>>>>> "+uri+contextRoot);

		return restTemplate.getForObject("http://ADDRESS-SERVICE/address-app/api/address/{id}", AddressResponse.class,
				id);
	}

	public List<EmployeeResponse> getAllEmployees() {

		List<Employee> employeeList = employeeRepo.findAll();

		List<EmployeeResponse> employeesResponse = Arrays
				.asList(modelMapper.map(employeeList, EmployeeResponse[].class));

		ResponseEntity<List<AddressResponse>> allAddress = addressClient.getAllAddress();
		List<AddressResponse> addressResponse = allAddress.getBody();

		//2 list
		// employeesResponse (DB Call)
		// addressResponse (Rest Call)
		
		employeesResponse.forEach(employee -> {

			for (AddressResponse addrResponse : addressResponse) {

				if (addrResponse.getId() == employee.getId()) {
					employee.setAddressResponse(addrResponse);
				}
			}

		});

		return employeesResponse;
	}

}
