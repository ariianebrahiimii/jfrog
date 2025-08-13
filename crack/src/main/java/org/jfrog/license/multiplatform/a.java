package org.jfrog.license.multiplatform;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Iterator;
import java.util.Map;
import java.util.StringJoiner;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.jfrog.license.api.Product;
import org.jfrog.license.exception.LicenseException;

public class a
{
	private b a;
	private Signature b;

	public a() throws LicenseException {
		this.a = new b();
	}

	public String a(final PrivateKey privateKey, final Map<String, Product> map, final boolean b) throws LicenseException, IOException {
		final License license = new License();
		if (b) {
			license.setValidateOnline(true);
		}
		else {
			license.setVersion(1);
		}
		for (final Map.Entry<String, Product> entry : map.entrySet()) {
			license.getProducts().put(entry.getKey(), new SignedProduct(entry.getValue(), privateKey, this.b));
		}
		return this.a(privateKey, license);
	}

	private String a(final PrivateKey privateKey, final License license) throws LicenseException {
		byte[] array;
		if (license.getVersion() > 1) {
			array = this.a.a(new SignedLicense(license, privateKey, this.b));
		}
		else {
			array = this.a.a(license);
		}
		return this.a(array);
	}

	@Deprecated
	public String a(final PrivateKey privateKey, final Map<String, Product> map) throws LicenseException, IOException {
		return this.a(privateKey, map, false);
	}

	public License a(final String s, final PublicKey publicKey) throws LicenseException {
		byte[] array = Base64.decodeBase64(s);
		try {
			final SignedLicense signedLicense = this.a.a(array, SignedLicense.class);
			signedLicense.verify(publicKey, this.b);
			array = Base64.decodeBase64(signedLicense.getLicense().getBytes());
			return this.b(publicKey, array);
		}
		catch (SignatureException | InvalidKeyException | LicenseException ex) {
			return this.a(publicKey, array);
		}
	}

	private License a(final PublicKey publicKey, final byte[] array) throws LicenseException {
		final License b = this.b(publicKey, array);
		if (b.getVersion() != 1) {
			throw new LicenseException("Failed to verify license version.");
		}
		return b;
	}

	private License b(final PublicKey publicKey, final byte[] array) throws LicenseException {
		final License license = this.a.a(array, License.class);
		if (license.getProducts() == null || license.getProducts().size() == 0) {
			throw new LicenseException("Failed to verify license, no products");
		}
		try {
			final Iterator<SignedProduct> iterator = license.getProducts().values().iterator();
			while (iterator.hasNext()) {
				iterator.next().verify(publicKey, this.b);
			}
		}
		catch (SignatureException | InvalidKeyException ex) {
			throw new LicenseException("Failed to verify license", ex);
		}
		return license;
	}

	public String a(final String s) throws LicenseException {
		final SignedLicense signedLicense = this.a.a(Base64.decodeBase64(s), SignedLicense.class);
		if (signedLicense.getLicense() == null) {
			return this.b(s);
		}
		return DigestUtils.sha1Hex(signedLicense.getSignature());
	}

	private String b(final String s) throws LicenseException {
		final License license = this.a.a(Base64.decodeBase64(s), License.class);
		final StringJoiner stringJoiner = new StringJoiner(",");
		license.getProducts().values().forEach(signedProduct -> stringJoiner.add(signedProduct.getSignature()));
		return DigestUtils.sha1Hex(this.a(stringJoiner.toString().getBytes()));
	}

	@Deprecated
	public String a(final String s, final Product product) throws LicenseException {
		return this.a(null, s, product, 1);
	}

	public String a(final PrivateKey privateKey, final String s, final Product product, final int version) throws LicenseException {
		if (privateKey == null && version > 1) {
			throw new IllegalArgumentException("privateKey need to be provided if the version > 1");
		}
		try {
			final License license = new License();
			license.setVersion(version);
			final SignedProduct signedProduct = new SignedProduct();
			signedProduct.setSignature(product.getSignature());
			product.setSignature(null);
			signedProduct.setProduct(new String(Base64.encodeBase64(new b().a(product)), "UTF-8"));
			license.getProducts().put(s, signedProduct);
			return this.a(privateKey, license);
		}
		catch (UnsupportedEncodingException ex) {
			throw new LicenseException("Failed to load license", ex);
		}
	}

	private String a(final byte[] array) {
		return StringUtils.newStringUtf8(Base64.encodeBase64(array, true));
	}

}
