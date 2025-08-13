package org.jfrog.license.legacy;

import org.codehaus.jackson.JsonParser;
import org.jfrog.license.api.Product;
import org.jfrog.license.exception.LicenseException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonFactory;
import java.io.IOException;
import org.jfrog.license.exception.LicenseRuntimeException;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.OutputStream;
import java.util.HashMap;

import org.codehaus.jackson.JsonEncoding;
import java.io.ByteArrayOutputStream;
//JsonLicenseSerializer
public class JsonLicenseSerializer
{
    public byte[] generate(final Object o) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            final JsonFactory a = new JsonFactory();
            final JsonGenerator jsonGenerator = a.createJsonGenerator((OutputStream)byteArrayOutputStream, JsonEncoding.UTF8);
            final ObjectMapper codec = new ObjectMapper(a);
            codec.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            jsonGenerator.setCodec((ObjectCodec)codec);
            jsonGenerator.writeObject(o);
        }
        catch (IOException ex) {
            throw new LicenseRuntimeException("Failed to serialize license", ex);
        }
        return byteArrayOutputStream.toByteArray();
    }
    
     public <T> HashMap<String, T> parse(final byte[] array) throws LicenseException {
        try {
            final JsonParser jsonParser = new JsonFactory().createJsonParser(array);
            jsonParser.setCodec((ObjectCodec) new ObjectMapper());
            TypeReference<HashMap<String, Product>> typeRef = new TypeReference<HashMap<String, Product>> (){};
            return jsonParser.getCodec().readValue(jsonParser, typeRef);
        }
        catch (IOException ex) {
            throw new LicenseException("License is invalid", ex);
        }
    }
}
