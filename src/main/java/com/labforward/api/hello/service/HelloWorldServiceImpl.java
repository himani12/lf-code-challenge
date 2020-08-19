package com.labforward.api.hello.service;

import com.labforward.api.core.exception.ResourceNotFoundException;
import com.labforward.api.core.validation.EntityValidator;
import com.labforward.api.hello.domain.Greeting;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.labforward.api.hello.constants.Messages.*;

@Service
public class HelloWorldServiceImpl implements HelloWorldService{

    private Map<String, Greeting> greetings;
    private EntityValidator entityValidator;

    public HelloWorldServiceImpl(EntityValidator entityValidator) {
        this.entityValidator = entityValidator;
        this.greetings = new HashMap<>(1);
        save(getDefault());
    }

    /**
     * Creates a new greeting with a new message
     * @param request Greeting object
     * @return Optional with new greeting if the creation was successful or empty if else
     */
    @Override
    public Greeting createGreeting(Greeting request) {
        entityValidator.validateCreate(request);
        request.setId(UUID.randomUUID().toString());
        return save(request);
    }

    /**
     * Updates a Greeting with a given Id
     * @param request Greeting object with the new message and Id
     * @return Optional with modified greeting if the update was successful or empty if else
     */
    @Override
    public Optional<Greeting> updateGreeting(Greeting request) {
        entityValidator.validateUpdate(request.getId(), request);
        Greeting greeting = greetings.get(request.getId());
        if (greeting == null) {
            return Optional.empty();
        }
        save(request);
        return Optional.of(greetings.get(request.getId()));
    }

    /**
     * Delete a Greeting with a given Id
     */
    @Override
    public void deleteGreeting(String greetingId) {
        Optional<Greeting> savedGreeting = getGreeting(greetingId);
        if (savedGreeting.isPresent()) {
            this.greetings.remove(savedGreeting.get().getId());
        }else {
            throw new ResourceNotFoundException(GREETING_NOT_FOUND);
        }
    }

    /**
     * Obtains a greeting with a given Id
     * @param id unique identifier of an Id
     * @return Optional with the found Greeting or empty if not found
     */
    @Override
    public Optional<Greeting> getGreeting(String id) {
        entityValidator.validateId(id, Greeting.class);
        Greeting greeting = greetings.get(id);
        if (greeting == null) {
            return Optional.empty();
        }

        return Optional.of(greeting);
    }

    private static Greeting getDefault() {
        return new Greeting(DEFAULT_ID, DEFAULT_MESSAGE);
    }

    private Greeting save(Greeting greeting) {
        this.greetings.put(greeting.getId(), greeting);

        return greeting;
    }
}
