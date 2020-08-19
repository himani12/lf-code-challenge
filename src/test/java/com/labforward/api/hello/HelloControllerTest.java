package com.labforward.api.hello;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labforward.api.common.MVCIntegrationTest;
import com.labforward.api.hello.domain.Greeting;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static com.labforward.api.hello.constants.Messages.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloControllerTest extends MVCIntegrationTest {

	private static final String HELLO_LUKE = "Hello Luke";

	@Test
	public void getHelloIsOKAndReturnsValidJSON() throws Exception {
		mockMvc.perform(get("/hello"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(DEFAULT_ID)))
				.andExpect(jsonPath("$.message", is(DEFAULT_MESSAGE)));
	}

	@Test
	public void getHelloWithRandomIdReturnsNotFound() throws Exception{
		UUID randomId = UUID.randomUUID();
		mockMvc.perform(get("/hello/"+ randomId))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", containsString(GREETING_NOT_FOUND)));
	}

	@Test
	public void returnsBadRequestWhenMessageMissing() throws Exception {
		String body = "{}";
		mockMvc.perform(post("/hello").content(body)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.validationErrors", hasSize(1)))
				.andExpect(jsonPath("$.validationErrors[*].field", contains("message")));
	}

	@Test
	public void returnsBadRequestWhenUnexpectedAttributeProvided() throws Exception {
		String body = "{ \"tacos\":\"value\" }}";
		mockMvc.perform(post("/hello").content(body).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.message", containsString(BAD_REQUEST)));
	}

	@Test
	public void returnsBadRequestWhenMessageEmptyString() throws Exception {
		Greeting emptyMessage = new Greeting("");
		final String body = getGreetingBody(emptyMessage);

		mockMvc.perform(post("/hello").content(body)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.validationErrors", hasSize(1)))
				.andExpect(jsonPath("$.validationErrors[*].field", contains("message")));
	}

	@Test
	public void createOKWhenRequiredGreetingProvided() throws Exception {
		Greeting hello = new Greeting(HELLO_LUKE);
		final String body = getGreetingBody(hello);

		mockMvc.perform(post("/hello").contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.message", is(hello.getMessage())));
	}

	@Test
	public void updateOKWhenRequiredGreetingProvided() throws Exception {
		final ObjectMapper objectMapper = new ObjectMapper();
		final String updatedMessage = "Updated Message";

		Greeting hello = new Greeting(HELLO_LUKE);
		final String body = getGreetingBody(hello);

		MvcResult result = mockMvc.perform(post("/hello")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		Greeting savedGreeting = objectMapper.readValue(content, Greeting.class);

		savedGreeting.setMessage(updatedMessage);
		final String updatedRequestBody = getGreetingBody(savedGreeting);

		mockMvc.perform(put("/hello")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updatedRequestBody))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is(updatedMessage)));
	}

	@Test
	public void UpdateReturnsBadRequestWhenIdAndMessageMissing() throws Exception {
		String body = "{}";
		mockMvc.perform(put("/hello/")
				.content(body)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.validationErrors", hasSize(1)))
				.andExpect(jsonPath("$.validationErrors[*].field", contains("id")));
	}

	@Test
	public void deleteReturnsResourceNotFoundOnInavlidID() throws Exception {
		String id= UUID.randomUUID().toString();
		mockMvc.perform(delete("/hello/"+id))
				.andExpect(status().isNotFound());
	}

	private String getGreetingBody(Greeting greeting) throws JSONException {
		JSONObject json = new JSONObject().put("message", greeting.getMessage());

		if (greeting.getId() != null) {
			json.put("id", greeting.getId());
		}

		return json.toString();
	}
}
