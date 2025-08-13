package org.jfrog.license.exception;

public class LicenseRuntimeException extends RuntimeException
{
    public LicenseRuntimeException(final String s) {
        super(s);
    }
    
    public LicenseRuntimeException(final String s, final Exception ex) {
        super(s, ex);
    }
}
