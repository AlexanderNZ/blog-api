package communityblogger.services;

import communityblogger.domain.BlogEntry;
import communityblogger.domain.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
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

    @Path("/user")
    @POST
    @Consumes("application/xml")
    Response createUser(User user);

    @Path("/user/{username}")
    @GET
    @Produces("application/xml")
    Response retrieveUser(@PathParam("username") String username);

    @Path("/blog/")
    @POST
    @Consumes("application/xml")
    Response createBlogEntry(BlogEntry blogEntry, @CookieParam("username") String username);

    @Path("/blog/{blogId}")
    @GET
    @Produces("application/xml")
    Response retrieveBlogEntry(@PathParam("blogId") String blogId);

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
