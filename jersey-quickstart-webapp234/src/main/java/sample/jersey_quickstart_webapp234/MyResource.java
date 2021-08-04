package sample.jersey_quickstart_webapp234;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Test;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	@Path("/text")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

	@Path("/json")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Test getIt2() {

		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd");

		Test ret = new Test();
		try {
			ret.date = sb.parse("2017-1-1");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		ret.name = "test taro";
		return ret;
	}
}
