package com.labforward.api.hello.service;

import com.labforward.api.hello.domain.Greeting;

import java.util.List;
import java.util.Optional;

public interface HelloWorldService {

    Greeting createGreeting(Greeting request);

    Optional<Greeting> updateGreeting(String id, Greeting request);

    Optional<Greeting> getGreeting(String id);

    List<Greeting> getGreetings();

    void deleteGreeting(String greetingId);
}
