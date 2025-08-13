package org.jfrog.license.legacy;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.jfrog.license.a.a;
import org.jfrog.license.exception.LicenseException;
import org.jfrog.license.exception.LicenseRuntimeException;

@JsonAutoDetect({ JsonMethod.NONE })
@JsonPropertyOrder({ "version", "license", "signature" })
public class SignedLicense
{
	private static final int COMPATIBILITY_VERSION = 1;
	@JsonProperty
	private final int version = 1;
	private static final String ERR_MESSAGE;
	@JsonProperty
	private byte[] license;
	@JsonProperty
	private byte[] signature;

	static {
		ERR_MESSAGE = new a(new long[] { 3761677959916763763L, 6997034839971895555L, 1961509552088746312L, -4033908949008504127L }).toString();
	}

	public SignedLicense() {
	}

	public int getVersion() {
		return 1;
	}


	public String getSignature() {
		return StringUtils.newStringUtf8(Base64.encodeBase64(this.signature, false));
	}

	private void sign(final PrivateKey privateKey, final Signature signature, final byte[] array) throws InvalidKeyException, SignatureException, IOException {
		signature.initSign(privateKey);
		signature.update(array.clone());
		this.signature = signature.sign();
	}

	public void verify(final PublicKey publicKey, final Signature signature) throws SignatureException, InvalidKeyException, LicenseException {
		signature.initVerify(publicKey);
		signature.update(this.license);
		if (!signature.verify(this.signature)) {
			throw new LicenseException("License verification failed");
		}
	}
}
