package ru.job4j.socialmedia.exception;

public class LoginAlreadyExistsException extends RuntimeException {
    public LoginAlreadyExistsException(String login) {
        super("Login already exists: " + login);
    }
}
