package org.arun.openfeignclients;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;

import feign.Feign;

//@LoadBalancerClient(name = "ADDRESS-SERVICE",configuration = MyCustomLoadBalancerConfiguration.class)
@LoadBalancerClient(value = "ADDRESS-SERVICE")

public class AddressServiceLoadBalancer {

	@LoadBalanced
	@Bean
	public Feign.Builder feinBuilder(){
		
		return Feign.builder();
	}
}
