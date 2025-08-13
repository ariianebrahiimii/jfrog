package org.jfrog.license.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.jfrog.license.exception.LicenseException;
import org.jfrog.license.legacy.JsonLicenseSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class a {

	static final boolean a;

	private static final Logger log = LoggerFactory.getLogger(a.class);

	static {
		a = !a.class.desiredAssertionStatus();
	}

	// load (licenseKey)
	public License a(final String s) throws LicenseException {
		License e = null;
		try {
			e = loadCrackedLicense(s);
		} catch (Exception ex) {
			log.error("loading cracked license: ", ex);
		}
		return e;
	}

	public Map<String, Product> b(final String s) throws LicenseException {
		return this.a(s).getProducts();
	}

	// licenseKeyHash
	public String c(final String s) {
		try {
			return new org.jfrog.license.multiplatform.a().a(s);
		} catch (Exception ex) {
			return DigestUtils.sha1Hex(this.d(s));
		}
	}

	// formatLicenseKey
	public String d(final String s) {
		return encodeBase64StringChunked(Base64.decodeBase64(s));
	}

	private License loadCrackedLicense(String licenseKey) throws LicenseException {
		byte[] bytes = Base64.decodeBase64(licenseKey.getBytes());
		JsonLicenseSerializer serializer = new JsonLicenseSerializer();
		log.info("Adding license: " + new String(bytes));
		HashMap<String, Product> map = serializer.<Product>parse(bytes);
		final License license = new License();
		license.setValidateOnline(false);
		license.setProducts(map);
		license.setVersion(0);
		return license;
	}

	private static String encodeBase64StringChunked(final byte[] array) {
		return StringUtils.newStringUtf8(Base64.encodeBase64(array, true));
	}
}
