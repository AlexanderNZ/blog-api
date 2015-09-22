package communityblogger.services;

import communityblogger.domain.BlogEntry;
import communityblogger.domain.Comment;
import communityblogger.domain.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("resources")
public interface BloggerResource {

    // TODO Follow blog entry

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

    @Path("/comments/{blogId}")
    @POST
    @Consumes("application/xml")
    Response createComment(Comment comment, @PathParam("blogId") String blogId, @CookieParam("username") String username);

    @Path("/comments/{blogId}")
    @GET
    @Produces("application/xml")
    Response retrieveComments(@PathParam("blogId") String blogId);

    @Path("/blogSet/")
    @GET
    @Produces("application/xml")
    Response retrieveBlogEntries();

    @Path("/followBlogEntry/{s}")
    @POST
    @Produces("application/xml")
    Response followBlogEntry(@PathParam("s") String s);
}
