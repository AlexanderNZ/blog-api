package communityblogger.services;

import java.util.Set;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("resources")
public interface BloggerResource {

	// TO DO:
	// Define one method for each of the operations in the Community Blogger
	// Web service contract. In each case, use appropriate JAX-RS annotations
	// to specify the URI pattern, media  type and HTTP method.
	//
	// The service contract comprises the 8 operations:
	// - Create user
	// - Retrieve user
	// - Create blog entry
	// - Retrieve blog entry
	// - Create comment
	// - Retrieve comments
	// - Retrieve blog entries
	// - Follow blog entry

	
	/**
	 * Useful operation to initialise the state of the Web service. This operation 
	 * can be invoked prior to executing each unit test.
	 */
	@PUT
	@Produces("application/xml")
	void initialiseContent();

	@Path("/createUser/{username}/{lastname}/{firstname}")
	@POST
	@Produces("application/xml")
	Response createUser(@PathParam("username") String username,
						@PathParam("lastname") String lastname,
						@PathParam("firstname") String firstname);

	@Path("/retrieveUser/{username}")
	@GET
	@Produces("application/xml")
	Response retrieveUser(@PathParam("username") String username);

	@Path("/createBlogEntry")
	@POST
	@Produces("application/xml")
	void createBlogEntry();

	@Path("/retrieveBlogEntry")
	@GET
	@Produces("application/xml")
	void retrieveBlogEntry();

	@Path("/createComment")
	@POST
	@Produces("application/xml")
	void createComment();

	@Path("/retrieveComments")
	@GET
	@Produces("application/xml")
	void retrieveComments();

	@Path("/retrieveBlogEntries")
	@POST
	@Produces("application/xml")
	void retrieveBlogEntries();

	@Path("/followBlogEntry")
	@PUT
	@Produces("application/xml")
	void followBlogEntry();
}