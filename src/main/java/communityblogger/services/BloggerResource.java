package communityblogger.services;

import communityblogger.domain.BlogEntry;
import communityblogger.domain.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

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

    @Path("/comments/")
    @POST
    @Produces("application/xml")
    Response createComment();

    @Path("/comments/")
    @GET
    @Produces("application/xml")
    Response retrieveComments(@PathParam("blogId") String blogId);

    @Path("/blogSets/")
    @GET
    @Produces("application/xml")
    Response retrieveBlogEntries();

    @Path("/followBlogEntry")
    @PUT
    @Produces("application/xml")
    void followBlogEntry();
}
