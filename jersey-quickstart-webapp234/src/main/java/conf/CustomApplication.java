package conf;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import sample.jersey_quickstart_webapp234.MyResource;

public class CustomApplication extends Application {
	//Add Service APIs
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new HashSet<Class<?>>();

		//register REST modules
		resources.add(MyResource.class);

		//Manually adding MOXyJSONFeature
		resources.add(org.glassfish.jersey.moxy.json.MoxyJsonFeature.class);

		//Configure Moxy behavior
		resources.add(JsonMoxyConfigurationContextResolver.class);

		return resources;
	}
}