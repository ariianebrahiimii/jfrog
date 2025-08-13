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
import org.jfrog.license.api.Product;
import org.jfrog.license.a.a;

public class SignedProduct
{
    private static final String ERR_MESSAGE;
    private String product;
    private String signature;
    
    static {
        ERR_MESSAGE = new a(new long[] { 3761677959916763763L, 6997034839971895555L, 1961509552088746312L, -4033908949008504127L }).toString();
    }
    
    public SignedProduct() {
    }
    
    public SignedProduct(final Product product, final PrivateKey privateKey, final Signature signature)
            throws LicenseException {
        try {
            this.sign(privateKey, signature, product);
        }
        catch (InvalidKeyException | SignatureException | IOException ex) {
            throw new LicenseException(SignedProduct.ERR_MESSAGE, ex);
        }
    }
    
    public void verify(final PublicKey publicKey, final Signature signature)
            throws SignatureException, InvalidKeyException, LicenseException {
        signature.initVerify(publicKey);
        signature.update(Base64.decodeBase64(this.product));
        if (!signature.verify(Base64.decodeBase64(this.signature))) {
            throw new LicenseException("License verification failed");
        }
    }
    
    public Product parseProduct() throws LicenseException {
        final Product product = new org.jfrog.license.multiplatform.b().a(Base64.decodeBase64(this.product.getBytes()), Product.class);
        if (product != null) {
            product.setSignature(this.signature);
        }
        return product;
    }
    
    private void sign(final PrivateKey privateKey, final Signature signature, final Product product)
            throws InvalidKeyException, SignatureException, IOException {
        final byte[] a = new org.jfrog.license.multiplatform.b().a(product);
        this.product = new String(Base64.encodeBase64(a), "UTF-8");
        signature.initSign(privateKey);
        signature.update(a.clone());
        this.signature = new String(Base64.encodeBase64(signature.sign()), Charset.forName("UTF-8"));
    }
    
    public String getProduct() {
        return this.product;
    }
    
    public void setProduct(final String product) {
        this.product = product;
    }
    
    public String getSignature() {
        return this.signature;
    }
    
    public void setSignature(final String signature) {
        this.signature = signature;
    }
}
