package com.labforward.api.hello.constants;

public class Messages {

    private Messages() {
    }

    // Common used strings
    public static final String GREETING_NOT_FOUND = "Greeting Not Found";
    public static final String DEFAULT_ID = "default";
    public static final String DEFAULT_MESSAGE = "Hello World!";

    // Validation messages
    public static final String MESSAGE_NO_ID_MATCH = "id provided does not match resource";
    public static final String MESSAGE_INVALID_ID = "Message Invalid Id";

    // Global config related
    public static final String BAD_REQUEST = "Bad Request";
    public static final String MESSAGE_UNRECOGNIZED_PROPERTY = "Unrecognized property: ";
}
