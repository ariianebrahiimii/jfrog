package org.jfrog.license.api;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.jfrog.license.legacy.License;
import org.jfrog.license.legacy.SignedLicense;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class Product {
	private String id;
	private String owner;
	private Date validFrom;
	private Date expires;
	private Type type;
	private boolean trial;
	private Map<String, String> properties;
	private String signature;

	public Product() {
		this.properties = new LinkedHashMap<String, String>();
		this.id = UUID.randomUUID().toString();
	}

	public Product(final Product product) {
		this.properties = new LinkedHashMap<String, String>();
		this.id = product.getId();
		this.owner = product.getOwner();
		this.validFrom = product.getValidFrom();
		this.expires = product.getExpires();
		this.type = product.getType();
		this.trial = product.isTrial();
		this.properties.putAll(product.getProperties());
		this.signature = product.getSignature();
	}



	public Product(License license) {
		this.properties = new LinkedHashMap<String, String>();

		this.id = String.valueOf(license.getProductId());
		this.owner = populateOwner(license);
		this.validFrom = license.getValidFrom();
		this.expires = license.getValidUntil();
		this.type = Type.fromLegacyType(license.getType());
		this.trial = License.Type.TRIAL.equals(license.getType());
	}

	private String populateOwner(final License license) {
		if (StringUtils.isNotBlank(license.getSubject())) {
			return license.getSubject();
		}
		if (StringUtils.isNotBlank(license.getCompany())) {
			return license.getCompany();
		}
		if (StringUtils.isNotBlank(license.getFirstName())) {
			return String.valueOf(license.getFirstName()) + " " + license.getLastName();
		}
		return "N/A";
	}

	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(final String owner) {
		this.owner = owner;
	}

	public Date getValidFrom() {
		return this.validFrom;
	}

	public void setValidFrom(final Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getExpires() {
		return this.expires;
	}

	public void setExpires(final Date expires) {
		this.expires = expires;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(final Type type) {
		this.type = type;
	}

	public boolean isTrial() {
		return this.trial;
	}

	public void setTrial(final boolean trial) {
		this.trial = trial;
	}

	public Map<String, String> getProperties() {
		return this.properties;
	}

	public void setProperties(final Map<String, String> properties) {
		this.properties = properties;
	}

	public String getSignature() {
		return this.signature;
	}

	public void setSignature(final String signature) {
		this.signature = signature;
	}

	public enum Type {
		OSS, COMMERCIAL, EDGE_TRIAL, EDGE, TRIAL, ENTERPRISE, ENTERPRISE_PLUS_TRIAL, ENTERPRISE_PLUS;
		private Type() {
		}

		public boolean isEnterprise() {
			return (this == ENTERPRISE) || (this == ENTERPRISE_PLUS) || (this == ENTERPRISE_PLUS_TRIAL);
		}

		public boolean isEdge() {
			return (this == EDGE) || (this == EDGE_TRIAL);
		}

		public boolean isEnterprisePlus() {
			return (this == ENTERPRISE_PLUS) || (this == ENTERPRISE_PLUS_TRIAL);
		}

		public static Type fromLegacyType(License.Type paramType) {
			switch (paramType.ordinal()) {
			case 1:
				return TRIAL;
			case 2:
				return COMMERCIAL;
			case 3:
				return OSS;
			case 4:
				return ENTERPRISE;
			}
			return TRIAL;
		}
	}
}
