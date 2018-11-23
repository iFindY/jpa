package de.arkadi.persistence.rest.exception;


public class ISBNNotFoundException extends Exception {

    // filed
    private String message;

    // constructor
    public ISBNNotFoundException() {
        this.message = "ISBN not found";
    }

    // second constructor
    public ISBNNotFoundException(String message) {
        this.message = message;
    }
}
