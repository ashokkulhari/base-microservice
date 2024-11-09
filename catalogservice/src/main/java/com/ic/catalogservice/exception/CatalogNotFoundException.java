package com.ic.catalogservice.exception;

import java.io.Serial;

/**
 * Exception class named {@link CatalogNotFoundException} thrown when a requested catalog cannot be found.
 */
public class CatalogNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5854010258697200749L;

    private static final String DEFAULT_MESSAGE = """
            Catalog not found!
            """;

    /**
     * Constructs a new CatalogNotFoundException with a default message.
     */
    public CatalogNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Constructs a new CatalogNotFoundException with a custom message appended to the default message.
     *
     * @param message the custom message indicating details about the exception
     */
    public CatalogNotFoundException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

}
