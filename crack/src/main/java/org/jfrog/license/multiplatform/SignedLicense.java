package org.jfrog.license.multiplatform;

import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Base64;
import java.security.PublicKey;
import java.io.IOException;
import java.security.SignatureException;
import java.security.InvalidKeyException;
import org.jfrog.license.exception.LicenseException;
import java.security.Signature;
import java.security.PrivateKey;

import org.jfrog.license.a.a;

public class SignedLicense
{
    private static final String ERR_MESSAGE;
    private String license;
    private String signature;
    
    static {
        ERR_MESSAGE = new a(new long[] { 3761677959916763763L, 6997034839971895555L, 1961509552088746312L, -4033908949008504127L }).toString();
    }
    
    public SignedLicense() {
    }
    
    public SignedLicense(final License license, final PrivateKey privateKey, final Signature signature)
            throws LicenseException {
        try {
            this.sign(privateKey, signature, license);
        }
        catch (InvalidKeyException | SignatureException | IOException ex) {
            throw new LicenseException(SignedLicense.ERR_MESSAGE, ex);
        }
    }
    
    public void verify(final PublicKey publicKey, final Signature signature) throws SignatureException, InvalidKeyException, LicenseException {
        if (this.license == null) {
        }
        signature.initVerify(publicKey);
        signature.update(Base64.decodeBase64(this.license));
        if (!signature.verify(Base64.decodeBase64(this.signature))) {
            throw new LicenseException("License verification failed");
        }
    }
    
    private void sign(final PrivateKey privateKey, final Signature signature, final License license) throws InvalidKeyException, SignatureException, IOException {
        final byte[] a = new org.jfrog.license.multiplatform.b().a(license);
        this.license = new String(Base64.encodeBase64(a), "UTF-8");
        signature.initSign(privateKey);
        signature.update(a.clone());
        this.signature = new String(Base64.encodeBase64(signature.sign()), Charset.forName("UTF-8"));
    }
    
    public String getLicense() {
        return this.license;
    }
    
    public void setLicense(final String license) {
        this.license = license;
    }
    
    public String getSignature() {
        return this.signature;
    }
    
    public void setSignature(final String signature) {
        this.signature = signature;
    }
}
