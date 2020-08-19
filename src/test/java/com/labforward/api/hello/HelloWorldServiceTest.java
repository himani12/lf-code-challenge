package com.labforward.api.hello;

import com.labforward.api.core.exception.EntityValidationException;
import com.labforward.api.hello.domain.Greeting;
import com.labforward.api.hello.service.HelloWorldService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static com.labforward.api.hello.constants.Messages.DEFAULT_ID;
import static com.labforward.api.hello.constants.Messages.DEFAULT_MESSAGE;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldServiceTest {

	private final String MESSAGE = "Hello Luke";

	@Autowired
	private HelloWorldService helloService;

	@Test
	public void getDefaultGreetingIsOK() {
		Optional<Greeting> greeting = helloService.getGreeting(DEFAULT_ID);
		Assert.assertTrue(greeting.isPresent());
		Assert.assertEquals(DEFAULT_ID, greeting.get().getId());
		Assert.assertEquals(DEFAULT_MESSAGE, greeting.get().getMessage());
	}

	@Test(expected = EntityValidationException.class)
	public void createGreetingWithEmptyMessageThrowsException() {
		final String EMPTY_MESSAGE = "";
		helloService.createGreeting(new Greeting(EMPTY_MESSAGE));
	}

	@Test(expected = EntityValidationException.class)
	public void createGreetingWithNullMessageThrowsException() {
		helloService.createGreeting(new Greeting(null));
	}

	@Test
	public void createGreetingOKWhenValidRequest() {
		Greeting request = new Greeting(MESSAGE);

		Greeting created = helloService.createGreeting(request);
		Assert.assertEquals(MESSAGE, created.getMessage());
	}

	@Test(expected = EntityValidationException.class)
	public void updateGreetingWhenMessageIsNull() {
		Greeting request = new Greeting();
		helloService.updateGreeting(request);
	}

	@Test(expected = EntityValidationException.class)
	public void getGreetingWithEmptyIDThrowsException(){
		helloService.getGreeting("");
	}

	@Test(expected = EntityValidationException.class)
	public void getGreetingsWithInvalidIdThrowsException() {
		final String invalidId = "InvalidID";
		helloService.getGreeting(invalidId);
	}

	@Test
	public void getGreetingReturnEmptyWhenIdNotPresent() {
		Optional<Greeting> retreived = helloService.getGreeting(UUID.randomUUID().toString());
		Assert.assertFalse(retreived.isPresent());
	}
	@Test
	public void updateGreetingOKWhenValidRequest() {
		Greeting request = new Greeting(MESSAGE);
		Greeting created = helloService.createGreeting(request);
		final String UPDATE_HELLO_LUKE = "Update Hello Luke";
		created.setMessage(UPDATE_HELLO_LUKE);
		Optional<Greeting> updated = helloService.updateGreeting(created);
		Assert.assertTrue(updated.isPresent());
		Assert.assertEquals(updated.get().getId(), created.getId());
		Assert.assertEquals(UPDATE_HELLO_LUKE, updated.get().getMessage());
	}

	@Test
	public void deleteGreetingWhenItIsExists() {
		final String HELLO_LUKE = "Hello Luke";
		Greeting request = new Greeting(HELLO_LUKE);
		request.setId(UUID.randomUUID().toString());
		Greeting created = helloService.createGreeting(request);
		helloService.deleteGreeting(created.getId());
		Optional<Greeting> retreived = helloService.getGreeting(created.getId());
		Assert.assertFalse(retreived.isPresent());
	}

	@Test(expected = EntityValidationException.class)
	public void deleteGreetingWhenMessageIsNull() {
		Greeting request = new Greeting();
		helloService.deleteGreeting(request.getId());
	}
}
