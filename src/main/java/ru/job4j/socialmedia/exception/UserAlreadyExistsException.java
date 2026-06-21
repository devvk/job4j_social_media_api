package ru.job4j.socialmedia.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String login) {
        super("Login already exists: " + login);
    }
}
