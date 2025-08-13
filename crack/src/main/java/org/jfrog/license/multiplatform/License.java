package org.jfrog.license.multiplatform;

import java.util.LinkedHashMap;
import java.util.Map;

public class License
{
    private int version;
    private Boolean validateOnline;
    private Map<String, SignedProduct> products;
    
    public License() {
        this.version = 2;
        this.validateOnline = null;
        this.products = new LinkedHashMap<String, SignedProduct>();
    }
    
    public int getVersion() {
        return this.version;
    }
    
    public void setVersion(final int version) {
        this.version = version;
    }
    
    public Map<String, SignedProduct> getProducts() {
        return this.products;
    }
    
    public void setProducts(final Map<String, SignedProduct> products) {
        this.products = products;
    }
    
    public Boolean getValidateOnline() {
        return this.validateOnline;
    }
    
    public void setValidateOnline(final Boolean validateOnline) {
        this.validateOnline = validateOnline;
    }
}
