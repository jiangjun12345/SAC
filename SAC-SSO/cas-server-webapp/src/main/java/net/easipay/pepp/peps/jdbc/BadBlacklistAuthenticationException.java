/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/
 */
package net.easipay.pepp.peps.jdbc;

import org.jasig.cas.authentication.handler.AuthenticationException;

/**
 * Generic Bad Credentials Exception. This can be thrown when the system knows
 * the credentials are not valid specificially because they are bad. Subclasses
 * can be specific to a certain type of Credentials
 * (BadUsernamePassowrdCredentials).
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.0
 */
public class BadBlacklistAuthenticationException extends
    AuthenticationException {

	/**
     * Static instance of class to prevent cost incurred by creating new
     * instance.
     */
    public static final BadBlacklistAuthenticationException ERROR = new BadBlacklistAuthenticationException();

    /** UID for serializable objects. */
	private static final long serialVersionUID = 1376255384843569554L;
    /**
     * Default constructor that does not allow the chaining of exceptions and
     * uses the default code as the error code for this exception.
     */
    private static final String CODE = "error.authentication.blacklist";

    /**
     * Default constructor that does not allow the chaining of exceptions and
     * uses the default code as the error code for this exception.
     */
    public BadBlacklistAuthenticationException() {
        super(CODE);
    }

    /**
     * Constructor to allow for the chaining of exceptions. Constructor defaults
     * to default code.
     * 
     * @param throwable the chainable exception.
     */
    public BadBlacklistAuthenticationException(final Throwable throwable) {
        super(CODE, throwable);
    }

    /**
     * Constructor method to allow for providing a custom code to associate with
     * this exception.
     * 
     * @param code the code to use.
     */
    public BadBlacklistAuthenticationException(final String code) {
        super(code);
    }

    /**
     * Constructor to allow for the chaining of exceptions and use of a
     * non-default code.
     * 
     * @param code the user-specified code.
     * @param throwable the chainable exception.
     */
    public BadBlacklistAuthenticationException(final String code,
        final Throwable throwable) {
        super(code, throwable);
    }
}
