package org.jfrog.license.legacy;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class License
{
    private int productId;
    private String productName;
    private String subject;
    private Date issued;
    private Date validFrom;
    private Date validUntil;
    private int quantity;
    private Type type;
    private Map<String, String> attributes;
    public static final String FIRSTNAME = "FIRSTNAME";
    public static final String LASTNAME = "LASTNAME";
    public static final String COMPANY = "COMPANY";
    public static final String EMAIL = "EMAIL";
    public static final String PURCHASE_ID = "PURCHASE_ID";
    public static final String REG_NAME = "REG_NAME";
    public static final String PHONE = "PHONE";
    public static final String FAX = "FAX";
    public static final String STREET = "STREET";
    public static final String ZIP = "ZIP";
    public static final String CITY = "CITY";
    public static final String STATE = "STATE";
    public static final String COUNTRY = "COUNTRY";
    
    public License() {
        this.quantity = 1;
        this.type = Type.TRIAL;
        this.attributes = new LinkedHashMap<String, String>();
    }
    
    public int getProductId() {
        return this.productId;
    }
    
    public void setProductId(final int productId) {
        this.productId = productId;
    }
    
    public String getProductName() {
        return this.productName;
    }
    
    public void setProductName(final String productName) {
        this.productName = productName;
    }
    
    public Date getIssued() {
        if (this.issued == null) {
            return null;
        }
        return new Date(this.issued.getTime());
    }
    
    public void setIssued(final Date date) {
        this.issued = new Date(date.getTime());
    }
    
    public String getSubject() {
        return this.subject;
    }
    
    public void setSubject(final String subject) {
        this.subject = subject;
    }
    
    public Date getValidFrom() {
        if (this.validFrom == null) {
            return null;
        }
        return new Date(this.validFrom.getTime());
    }
    
    public void setValidFrom(final Date date) {
        this.validFrom = new Date(date.getTime());
    }
    
    public Date getValidUntil() {
        if (this.validUntil == null) {
            return null;
        }
        return new Date(this.validUntil.getTime());
    }
    
    public void setValidUntil(final Date date) {
        this.validUntil = new Date(date.getTime());
    }
    
    public Type getType() {
        return this.type;
    }
    
    public void setType(final Type type) {
        this.type = type;
    }
    
    public void setAttribute(final String s, final String s2) {
        if (s == null) {
            return;
        }
        this.attributes.put(s, s2);
    }
    
    public String getAttribute(final String s) {
        if (s == null) {
            return null;
        }
        return this.attributes.get(s);
    }
    
    public String getEmail() {
        return this.getAttribute("EMAIL");
    }
    
    public void setEmail(final String s) {
        this.setAttribute("EMAIL", s);
    }
    
    public String getFirstName() {
        return this.getAttribute("FIRSTNAME");
    }
    
    public void setFirstName(final String s) {
        this.setAttribute("FIRSTNAME", s);
    }
    
    public String getLastName() {
        return this.getAttribute("LASTNAME");
    }
    
    public void setLastName(final String s) {
        this.setAttribute("LASTNAME", s);
    }
    
    public String getCompany() {
        return this.getAttribute("COMPANY");
    }
    
    public void setCompany(final String s) {
        this.setAttribute("COMPANY", s);
    }
    
    public boolean isExpired() {
        return new Date().after(this.validUntil);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final License license = (License)o;
        if (this.productId != license.productId) {
            return false;
        }
        if (this.quantity != license.quantity) {
            return false;
        }
        Label_0090: {
            if (this.attributes != null) {
                if (this.attributes.equals(license.attributes)) {
                    break Label_0090;
                }
            }
            else if (license.attributes == null) {
                break Label_0090;
            }
            return false;
        }
        if (!this.issued.equals(license.issued)) {
            return false;
        }
        if (!this.productName.equals(license.productName)) {
            return false;
        }
        Label_0155: {
            if (this.subject != null) {
                if (this.subject.equals(license.subject)) {
                    break Label_0155;
                }
            }
            else if (license.subject == null) {
                break Label_0155;
            }
            return false;
        }
        if (this.type != license.type) {
            return false;
        }
        if (this.validFrom != null) {
            if (this.validFrom.equals(license.validFrom)) {
                return this.validUntil.equals(license.validUntil);
            }
        }
        else if (license.validFrom == null) {
            return this.validUntil.equals(license.validUntil);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * this.productId + this.productName.hashCode()) + ((this.subject != null) ? this.subject.hashCode() : 0)) + this.issued.hashCode()) + ((this.validFrom != null) ? this.validFrom.hashCode() : 0)) + this.validUntil.hashCode()) + this.quantity) + this.type.hashCode()) + ((this.attributes != null) ? this.attributes.hashCode() : 0);
    }
    
    @Override
    public String toString() {
        return "Artifactory License {\n\tproductName='" + this.productName + '\'' + "\n\tproductId=" + this.productId + "\n\tsubject='" + this.subject + '\'' + "\n\tissued=" + this.issued + "\n\tvalidFrom=" + this.validFrom + "\n\tvalidUntil=" + this.validUntil + "\n\tquantity=" + this.quantity + "\n\ttype=" + this.type + "\n\tattributes=" + this.attributes + "\n}";
    }
    
    public enum Type
    {
        TRIAL("TRIAL", 0), 
        COMMERCIAL("COMMERCIAL", 1), 
        OSS("OSS", 2), 
        HA("HA", 3);
        
        private Type(final String s, final int n) {
        }
    }
}
