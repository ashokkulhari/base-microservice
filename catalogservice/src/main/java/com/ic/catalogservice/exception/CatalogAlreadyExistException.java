package com.ic.catalogservice.exception;

import java.io.Serial;

/**
 * Exception class named {@link CatalogAlreadyExistException} thrown when attempting to create a catalog that already exists.
 */
public class CatalogAlreadyExistException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 53457089789182737L;

    private static final String DEFAULT_MESSAGE = """
            Catalog already exist!
            """;

    /**
     * Constructs a new CatalogAlreadyExistException with a default message.
     */
    public CatalogAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Constructs a new CatalogAlreadyExistException with a custom message appended to the default message.
     *
     * @param message the custom message indicating details about the exception
     */
    public CatalogAlreadyExistException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

}
