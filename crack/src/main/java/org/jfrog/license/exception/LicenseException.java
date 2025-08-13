package org.jfrog.license.exception;

public class LicenseException extends Exception
{
    public LicenseException(final String s) {
        super(s);
    }
    
    public LicenseException(final String s, final Exception ex) {
        super(s, ex);
    }
}
