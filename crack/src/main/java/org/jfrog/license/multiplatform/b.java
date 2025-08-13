package org.jfrog.license.multiplatform;

import org.jfrog.license.exception.LicenseException;
import java.nio.charset.Charset;
import org.yaml.snakeyaml.Yaml;
import java.util.List;
import java.util.Arrays;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.representer.Representer;

class b
{
    private static final String[] a;
    
    static {
        a = new String[] { "validateOnline" };
    }
    
    byte[] a(final Object o) {
        return new Yaml((Representer)new Representer() {
            protected NodeTuple representJavaBeanProperty(final Object o, final Property property, final Object o2, final Tag tag) {
                final List<String> list = Arrays.asList(b.a);
                if (o2 == null && list.contains(property.getName())) {
                    return null;
                }
                return super.representJavaBeanProperty(o, property, o2, tag);
            }
        }).dumpAsMap(o).getBytes(Charset.forName("UTF-8"));
    }
    
     <T> T a(final byte[] array, final Class<T> clazz) throws LicenseException {
        final Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        final Yaml yaml = new Yaml(representer);
        try {
            return (T)yaml.loadAs(new String(array, "UTF-8"), (Class)clazz);
        }
        catch (Exception ex) {
            throw new LicenseException("License is invalid", ex);
        }
    }
}
