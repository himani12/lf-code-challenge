package com.labforward.api.hello.controller;

import com.labforward.api.core.creation.EntityCreatedResponse;
import com.labforward.api.core.exception.ResourceNotFoundException;
import com.labforward.api.hello.domain.Greeting;
import com.labforward.api.hello.service.HelloWorldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import static com.labforward.api.hello.constants.Messages.DEFAULT_ID;
import static com.labforward.api.hello.constants.Messages.GREETING_NOT_FOUND;

@RestController
@RequestMapping("/hello")
@Api(description= "Operations on greetings (Get, Create, Update, Delete)")
public class HelloController {

	private HelloWorldService helloWorldService;

	public HelloController(HelloWorldService helloWorldService) {
		this.helloWorldService = helloWorldService;
	}

	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	@ApiOperation(value = "Get default greeting.", response = Greeting.class)
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	@ResponseBody
	public Greeting helloWorld() {
		return getGreeting(DEFAULT_ID);
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = GREETING_NOT_FOUND) })
	@ApiOperation(value = "Get greeting by id.", response = Greeting.class)
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	@ResponseBody
	public Greeting getGreeting(@PathVariable String id) {
		return helloWorldService.getGreeting(id)
				.orElseThrow(() -> new ResourceNotFoundException(GREETING_NOT_FOUND));
	}

	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 422, message = "Bad Request") })
	@ApiOperation(value = "Create greeting.", response = Greeting.class)
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public EntityCreatedResponse createGreeting(@RequestBody Greeting request) throws URISyntaxException {
		Greeting greeting = helloWorldService.createGreeting(request);
		return new EntityCreatedResponse<>(greeting, new URI("/hello/".concat(greeting.getId())));
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 422, message = "Bad Request") })
	@ApiOperation(value = "Update existing greeting.", response = Greeting.class)
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping("/{id}")
	public Greeting updateGreeting(@PathVariable String id, @RequestBody Greeting request) {
		return helloWorldService.updateGreeting(id, request)
				.orElseThrow(() -> new ResourceNotFoundException(GREETING_NOT_FOUND));
	}

	@ApiResponses(value = { @ApiResponse(code = 202, message = "Deleted"),
			@ApiResponse(code = 422, message = "Bad Request") })
	@ApiOperation(value = "Delete greeting.", response = Greeting.class)
	@ResponseStatus(HttpStatus.ACCEPTED)
	@DeleteMapping("/{id}")
	public void deleteGreeting(@PathVariable String id) {
		helloWorldService.deleteGreeting(id);
	}

}
