package conf;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;

/**
 * https://howtodoinjava.com/jersey/jax-rs-jersey-moxy-json-example/
 */
@Provider
public class JsonMoxyConfigurationContextResolver implements ContextResolver<MoxyJsonConfig>
{

    private final MoxyJsonConfig config;

    public JsonMoxyConfigurationContextResolver()
    {
    	System.out.println("JsonMoxyConfigurationContextResolver");
        final Map<String, String> namespacePrefixMapper = new HashMap<String, String>();
        namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");

        config = new MoxyJsonConfig()
                .setNamespacePrefixMapper(namespacePrefixMapper)
                .setNamespaceSeparator(':')
                .setAttributePrefix("")
                .setValueWrapper("value")
                .property(JAXBContextProperties.JSON_WRAPPER_AS_ARRAY_NAME, true)
                .setFormattedOutput(true)
                .setIncludeRoot(true)
                .setMarshalEmptyCollections(true);
    }

    @Override
    public MoxyJsonConfig getContext(Class<?> objectType)
    {
        return config;
    }
}
