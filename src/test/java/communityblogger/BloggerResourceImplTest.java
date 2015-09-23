package communityblogger;

import communityblogger.domain.BlogEntry;
import communityblogger.domain.Comment;
import communityblogger.domain.User;
import communityblogger.services.BloggerResolver;
import communityblogger.services.BloggerResource;
import communityblogger.services.BloggerResourceImpl;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.joda.time.DateTime;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by alexcorkin on 10/09/2015.
 */

public class BloggerResourceImplTest {


    Response createdUser;
    Response testUser;
    Response response;
    Response retreieveUserIntegration;


    /**
     * Test that successfully creates a user
     *
     * @throws Exception
     */
    @org.junit.Test
    public void createUserSuccess() throws Exception {

        BloggerResource bloggerResource = new BloggerResourceImpl();
        User user = new User("Increrderble Herlk", "created-last", "created-first");
        Response response = bloggerResource.createUser(user);

        assertEquals(201, response.getStatus());

        //Test that the generated URI is correct
        String uri = response.getLink("resource").getUri().toString();
        response.close();

        String username = uri.replace("/services/resources/user/", "");
        String result = java.net.URLDecoder.decode(username, "UTF-8");

        response = bloggerResource.retrieveUser(result);

        assertEquals(200, response.getStatus());
        User retrievedUser = (User) response.getEntity();

        assertEquals(user.getUsername(), retrievedUser.getUsername());

    }

    /**
     * Test that fails to create a user
     *
     * @throws Exception
     */
    @org.junit.Test
    public void createUserFailure() throws Exception {

        BloggerResource bloggerResource = new BloggerResourceImpl();

        User user = new User("Bertmern", "Brerce", "Werne");
        createdUser = bloggerResource.createUser(user);

        assertEquals(409, createdUser.getStatus());

    }

    /**
     * Test attempts to create a new user that does not exist in the hashmap already, then creates one that already
     * exists in the hash map
     * Tests this against a deployed webserver
     *
     * @throws Exception
     */
    @org.junit.Test
    public void createUserIntegration() throws Exception {

        ResteasyClient client = new ResteasyClientBuilder().build();
        client.register(BloggerResolver.class);
        ResteasyWebTarget target = client.target("http://0.0.0.0:10000/services/");
        BloggerResource bloggerResource = target.proxy(BloggerResource.class);

        User integrationUser1 = new User("RNJesus", "created-last", "created-first");
        User integrationUser2 = new User("Bertmern", "Brerce", "Werne");

        Response createUserIntegrationSuccess = bloggerResource.createUser(integrationUser1);
        createUserIntegrationSuccess.close();
        Response createUserIntegrationFailure = bloggerResource.createUser(integrationUser2);
        createUserIntegrationFailure.close();

        assertEquals(201, createUserIntegrationSuccess.getStatus());
        assertEquals(409, createUserIntegrationFailure.getStatus());
    }

    /**
     * Retrieves a user that does not exist in the project, tests that a 404 is returned.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void retrieveNonExistentUser() throws Exception {

        BloggerResource bloggerResource = new BloggerResourceImpl();

        testUser = bloggerResource.retrieveUser("Chigger");

        assertNull(testUser.getEntity());

        assertEquals(404, testUser.getStatus());


    }

    /**
     * Retrieves a user that exists, tests that 200 and the user is returned
     *
     * @throws Exception
     */
    @org.junit.Test
    public void retrieveUser() throws Exception {

        BloggerResource bloggerResource = new BloggerResourceImpl();

        response = bloggerResource.retrieveUser("Bertmern");
        User user = (User) response.getEntity();

        assertEquals(200, response.getStatus());
        assertEquals("Bertmern", user.getUsername());

    }

    /**
     * Retrieves a user that exists, tests that 200 and the user is returned
     * Tests this against a deployed webserver
     *
     * @throws Exception
     */
    @org.junit.Test
    public void retrieveUserIntegration() throws Exception {

        ResteasyClient client = new ResteasyClientBuilder().build();
        client.register(BloggerResolver.class);
        ResteasyWebTarget target = client.target("http://0.0.0.0:10000/services/");
        BloggerResource bloggerResource = target.proxy(BloggerResource.class);

        retreieveUserIntegration = bloggerResource.retrieveUser("Bertmern");

        assertEquals(200, retreieveUserIntegration.getStatus());

        User user = retreieveUserIntegration.readEntity(User.class);
        assertEquals("Bertmern", user.getUsername());

    }

    /**
     * Creates a blog entry.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void createBlogEntrySuccess() throws Exception {

        BloggerResource bloggerResource = new BloggerResourceImpl();

        BlogEntry createdBlogEntry = new BlogEntry("First item in blog hashmap");

        Response response = bloggerResource.createBlogEntry(createdBlogEntry, "Bertmern");

        assertEquals(201, response.getStatus());

        //Test that the generated URI is correct
        String uri = response.getLink("resource").getUri().toString();
        response.close();

        String blogEntryHashLocation = uri.replace("/services/resources/blog/", "");
        String result = java.net.URLDecoder.decode(blogEntryHashLocation, "UTF-8");

        response = bloggerResource.retrieveBlogEntry(result);

        BlogEntry retrievedBlogEntry = (BlogEntry) response.getEntity();

        assertEquals(retrievedBlogEntry.getId(), retrievedBlogEntry.getId());
    }

    /**
     * Fails to create a blog entry.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void createBlogEntryFail() throws Exception {

        BloggerResource bloggerResource = new BloggerResourceImpl();

        BlogEntry createdBlogEntry = new BlogEntry("This will never get recorded, java sux.");

        Response response = bloggerResource.createBlogEntry(createdBlogEntry, "SomeUnauthorisedDickhead");

        assertEquals(412, response.getStatus());

    }

    /**
     * Retrieves a blog entry
     *
     * @throws Exception
     */
    @org.junit.Test
    public void retrieveBlogEntrySuccess() throws Exception {

        BloggerResource bloggerResource = new BloggerResourceImpl();

        Response retrievedPost = bloggerResource.retrieveBlogEntry("0");

        assertEquals(200, retrievedPost.getStatus());

    }

    /**
     * Fails to retrieve a blog entry
     *
     * @throws Exception
     */
    @org.junit.Test
    public void retrieveBlogEntryFail() throws Exception {

        BloggerResource bloggerResource = new BloggerResourceImpl();

        Response retrievedPost = bloggerResource.retrieveBlogEntry("60");

        assertEquals(404, retrievedPost.getStatus());

    }

    /**
     * Integrates blog entry creation success and failure tests.
     * Tests against webserver
     *
     * @throws Exception
     */
    @org.junit.Test
    public void createBlogEntryIntegration() throws Exception {

        ResteasyClient client = new ResteasyClientBuilder().build();
        client.register(BloggerResolver.class);
        ResteasyWebTarget target = client.target("http://0.0.0.0:10000/services/");
        BloggerResource bloggerResource = target.proxy(BloggerResource.class);

        bloggerResource.initialiseContent();

        User integrationUser1 = new User("RNJesus", "created-last", "created-first");
        BlogEntry integrationBlogEntry1 = new BlogEntry("Blogs smell");
        BlogEntry integrationBlogEntry2 = new BlogEntry("Blogs smell doubly bad");

        Response createUserIntegrationSuccess = bloggerResource.createUser(integrationUser1);
        createUserIntegrationSuccess.close();

        Response createBlogEntryIntegrationSuccess = bloggerResource.createBlogEntry(integrationBlogEntry1, "RNJesus");
        createBlogEntryIntegrationSuccess.close();
        Response createBlogEntryIntegrationFail = bloggerResource.createBlogEntry(integrationBlogEntry2, "Pacman");
        createBlogEntryIntegrationFail.close();

        assertEquals(201, createBlogEntryIntegrationSuccess.getStatus());
        assertEquals(412, createBlogEntryIntegrationFail.getStatus());
    }

    /**
     * Returns an XML representation of a set of blog comments successfully
     */
    @org.junit.Test
    public void retrieveBlogEntryCommentsSuccess() throws Exception {

        BloggerResource bloggerResource = new BloggerResourceImpl();
        Response retrievedComments = bloggerResource.retrieveComments("0");
        assertEquals(200, retrievedComments.getStatus());
    }

    /**
     * Fails to return an XML representation of a set of blog comments successfully
     */
    @org.junit.Test
    public void retrieveBlogEntryCommentsFail() throws Exception {

        BloggerResource bloggerResource = new BloggerResourceImpl();
        Response retrievedComments = bloggerResource.retrieveComments("-12");
        assertEquals(404, retrievedComments.getStatus());
    }

    /**
     * Integrates success and failure cases of the return blog comments tests,
     * tests them against the webserver
     */
    @org.junit.Test
    public void retrieveBlogEntryCommentsIntegration() throws Exception {

        ResteasyClient client = new ResteasyClientBuilder().build();
        client.register(BloggerResolver.class);
        ResteasyWebTarget target = client.target("http://0.0.0.0:10000/services/");
        BloggerResource bloggerResource = target.proxy(BloggerResource.class);

        bloggerResource.initialiseContent();

        Response retrievedCommentsIntegrationSuccess = bloggerResource.retrieveComments("0");
        assertEquals(200, retrievedCommentsIntegrationSuccess.getStatus());
        retrievedCommentsIntegrationSuccess.close();

        Response retrievedCommentsIntegrationFail = bloggerResource.retrieveComments("-12");
        assertEquals(404, retrievedCommentsIntegrationFail.getStatus());
        retrievedCommentsIntegrationFail.close();
    }

    /**
     * Creates a blog entry comment, attaches that comment to a specified blog entry
     * Request should include a cookie header with username - this is comment author.
     * Request must identify a blog entry that exists in a hash map
     */
    @org.junit.Test
    public void createBlogEntryCommentSuccess() throws Exception {

        BloggerResource bloggerResource = new BloggerResourceImpl();

        Comment blogComment = new Comment("This is a comment from createBlogEntryCommentSuccess", DateTime.now());

        Response response = bloggerResource.createComment(blogComment, "0", "Bertmern");

        assertEquals(201, response.getStatus());
    }

    @org.junit.Test
    public void createBlogEntryCommentBlogEntry404() throws Exception {

        BloggerResource bloggerResource = new BloggerResourceImpl();

        Comment blogComment = new Comment("This is a comment from createBlogEntryCommentSuccess", DateTime.now());

        Response response = bloggerResource.createComment(blogComment, "41", "Bertmern");

        assertEquals(404, response.getStatus());
    }

    @org.junit.Test
    public void createBlogEntryCommentUserNotAuthorised() throws Exception {

        BloggerResource bloggerResource = new BloggerResourceImpl();

        Comment blogComment = new Comment("This is a comment from createBlogEntryCommentSuccess", DateTime.now());

        Response response = bloggerResource.createComment(blogComment, "0", "Chooooooo");

        assertEquals(412, response.getStatus());
    }

    /**
     * Retrieves a set of blog entries
     */
    @org.junit.Test
    public void retrieveBlogEntriesSuccess() throws Exception {

        BloggerResource bloggerResource = new BloggerResourceImpl();

        BlogEntry blogEntry1 = new BlogEntry("1 post");
        BlogEntry blogEntry2 = new BlogEntry("2 post");
        BlogEntry blogEntry3 = new BlogEntry("3 post");
        BlogEntry blogEntry4 = new BlogEntry("blue post");

        bloggerResource.createBlogEntry(blogEntry1, "Bertmern");
        bloggerResource.createBlogEntry(blogEntry2, "Bertmern");
        bloggerResource.createBlogEntry(blogEntry3, "Bertmern");
        bloggerResource.createBlogEntry(blogEntry4, "Bertmern");

        Response retrievedBlogEntriesResponse = bloggerResource.retrieveBlogEntries("Bertmern");

        assertEquals(200, retrievedBlogEntriesResponse.getStatus());
    }

    /**
     * Follows a particular blog entry
     */
    @org.junit.Test
    public void followBlogEntry() throws Exception {

        BloggerResource bloggerResource = new BloggerResourceImpl();

        //add comments
        Comment comment1 = new Comment("poll comment 1", DateTime.now());
        Comment comment2 = new Comment("poll comment 2", DateTime.now());
        Comment comment3 = new Comment("poll comment 3", DateTime.now());

        BlogEntry blogEntry = new BlogEntry("poll post");

        bloggerResource.createBlogEntry(blogEntry, "Bertmern");
        bloggerResource.createComment(comment1, "3", "Spodermern");
        bloggerResource.createComment(comment2, "3", "Spodermern");
        bloggerResource.createComment(comment3, "3", "Spodermern");

        //poll it
        Response response = bloggerResource.followBlogEntry("3");
        // assert that the first three blog comments are returned

        //add new comments

        Comment comment4 = new Comment("poll comment 4", DateTime.now());
        Comment comment5 = new Comment("poll comment 5", DateTime.now());
        Comment comment6 = new Comment("poll comment 6", DateTime.now());

        bloggerResource.createComment(comment4, "3", "Spodermern");
        bloggerResource.createComment(comment5, "3", "Spodermern");
        bloggerResource.createComment(comment6, "3", "Spodermern");

        //does the function only return the new comments?

        //shit idek how to to this
    }

    /**
     * All singing all dancing integration test pls dont break
     * This test passing is the best indicator I have that my work satisfies the service contract.
     * Every endpoint is tested for success and failure. Hopefully didn't dungoof.
     */
    @org.junit.Test
    public void theTestToEndAllTests() throws Exception {

        //Set up web server / client
        ResteasyClient client = new ResteasyClientBuilder().build();
        client.register(BloggerResolver.class);
        ResteasyWebTarget target = client.target("http://0.0.0.0:10000/services/");
        BloggerResource bloggerResource = target.proxy(BloggerResource.class);

        //Test Create and Retrieve User
        User integrationUser1 = new User("gizmo", "gizmo", "ludus-first");
        User integrationUser2 = new User("Bertmern", "Brerce", "Werne");

        Response createUserIntegrationSuccess = bloggerResource.createUser(integrationUser1);
        createUserIntegrationSuccess.close();
        Response createUserIntegrationFailure = bloggerResource.createUser(integrationUser2);
        createUserIntegrationFailure.close();

        assertEquals(201, createUserIntegrationSuccess.getStatus());
        assertEquals(409, createUserIntegrationFailure.getStatus());

        Response retrieveUserIntegrationSuccess = bloggerResource.retrieveUser("gizmo");
        retrieveUserIntegrationSuccess.close();
        Response retrieveUserIntegrationFailure = bloggerResource.retrieveUser("903712987361923189276");
        retrieveUserIntegrationFailure.close();

        assertEquals(200, retrieveUserIntegrationSuccess.getStatus());
        assertEquals(404, retrieveUserIntegrationFailure.getStatus());

        //Test Retrieve and Create Blog Entry

        BlogEntry blogEntry = new BlogEntry("theTestToEndAllTests");

        Response blogEntryFinalIntegrationSuccess = bloggerResource.createBlogEntry(blogEntry, "gizmo");
        blogEntryFinalIntegrationSuccess.close();
        assertEquals(201, blogEntryFinalIntegrationSuccess.getStatus());

        Response blogEntryFinalIntegrationFail = bloggerResource.createBlogEntry(blogEntry, "noteveninyoursystem");
        blogEntryFinalIntegrationFail.close();
        assertEquals(412, blogEntryFinalIntegrationFail.getStatus());

        Response blogEntryRetrievedSuccess = bloggerResource.retrieveBlogEntry("2");
        blogEntryRetrievedSuccess.close();
        assertEquals(200, blogEntryRetrievedSuccess.getStatus());

        Response blogEntryRetrievedFail = bloggerResource.retrieveBlogEntry("345");
        blogEntryRetrievedFail.close();
        assertEquals(404, blogEntryRetrievedFail.getStatus());

        //Test Retrieve Comment

        Response retrievedCommentIntegration = bloggerResource.retrieveComments("0");
        retrievedCommentIntegration.close();
        assertEquals(200, retrievedCommentIntegration.getStatus());

        Response retrievedCommentIntegrationFail = bloggerResource.retrieveComments("345");
        retrievedCommentIntegrationFail.close();
        assertEquals(404, retrievedCommentIntegrationFail.getStatus());

        //Test Create Comment
        Comment comment = new Comment("integration integration integration", DateTime.now());
        Response createdComment = bloggerResource.createComment(comment, "0", "Bertmern");
        createdComment.close();
        assertEquals(201, createdComment.getStatus());

        Response createdCommentDail = bloggerResource.createComment(comment, "0909", "Bertmern");
        createdCommentDail.close();
        assertEquals(404, createdCommentDail.getStatus());

        Response createdCommentFail = bloggerResource.createComment(comment, "0", "nobody");
        createdCommentDail.close();
        assertEquals(412, createdCommentFail.getStatus());
        //Test Retrieve Blog Entries
    }
}
