package com.ncs503.Babybook.exception;

/**
 * @author Leonardo Terlizzi
 */

public class UserProfileAlreadyExistsException extends Exception{

    public UserProfileAlreadyExistsException(String message) {
        super(message);
    }
}
