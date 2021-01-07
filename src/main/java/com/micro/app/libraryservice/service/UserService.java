package com.micro.app.libraryservice.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.micro.app.libraryservice.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class UserService {

	@Value("http://${user.service.name}/api/${user.service.version}/users")
	private String userServiceUrl;

	@Autowired
	private RestTemplate restTemplate;

	public List<User> fetchAllUsers() {
		ResponseEntity<User[]> response = restTemplate.getForEntity(userServiceUrl, User[].class);
		return Arrays.asList(response.getBody());
	}

	@HystrixCommand(fallbackMethod = "getFallbackUserById", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		})
	public User fetchUserById(Long id) {
		return restTemplate.getForEntity(userServiceUrl + "/" + id, User.class).getBody();
	}

	public User addUser(User user) {
		return restTemplate.postForEntity(userServiceUrl, user, User.class).getBody();
	}

	public void deleteUser(Long id) {
		restTemplate.delete(userServiceUrl + "/" + id);
	}

	public void updateUser(User user, Long id) {
		restTemplate.put(userServiceUrl+"/"+id, user);
	}
	
	public User getFallbackUserById(Long id) {
		return new User(id,"Not Available","Not Available");
	}

}
