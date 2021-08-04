package conf;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;


@ApplicationPath("resources")
public class MyApplication extends ResourceConfig {
	public MyApplication() {
		System.out.println("MyApplication const");
		packages("rest");
	}
}
