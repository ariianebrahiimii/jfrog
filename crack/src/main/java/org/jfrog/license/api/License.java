package org.jfrog.license.api;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class License
{
	private int version;
	private Boolean validateOnline;
	private Map<String, Product> products;

	public License() {
		this.products = new LinkedHashMap<String, Product>();
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public Boolean getValidateOnline() {
		return this.validateOnline;
	}

	public void setValidateOnline(final Boolean validateOnline) {
		this.validateOnline = validateOnline;
	}

	public Map<String, Product> getProducts() {
		return this.products;
	}

	public void setProducts(final Map<String, Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		String r = "License: products";
		for (Entry<String, Product> e : this.products.entrySet()) {
			r += e.getKey() + ":" + e.getValue() + "\n";
		}
		return r;
	}
}
