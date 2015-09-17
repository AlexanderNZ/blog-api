package communityblogger;

import communityblogger.domain.BlogEntry;
import communityblogger.domain.User;
import communityblogger.services.BloggerResolver;
import communityblogger.services.BloggerResource;
import communityblogger.services.BloggerResourceImpl;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

//        //Test that the generated URI is correct
//        String uri = response.getLink("resource").getUri().toString();
//        response.close();
//
//        String username = uri.replace("/services/resources/blog/", "");
//        String result = java.net.URLDecoder.decode(username, "UTF-8");
//
//        response = bloggerResource.retrieveBlogEntry(result);
//
//        assertEquals(200, response.getStatus());
//        BlogEntry retrievedBlogEntry = (BlogEntry) response.getEntity();
//
//        assertEquals(retrievedBlogEntry.getId(), retrievedBlogEntry.getId());

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

//        //Test that the generated URI is correct
//        String uri = response.getLink("resource").getUri().toString();
//        response.close();
//
//        String username = uri.replace("/services/resources/blog/", "");
//        String result = java.net.URLDecoder.decode(username, "UTF-8");
//
//        response = bloggerResource.retrieveBlogEntry(result);
//
//        assertEquals(200, response.getStatus());
//        BlogEntry retrievedBlogEntry = (BlogEntry) response.getEntity();
//
//        assertEquals(retrievedBlogEntry.getId(), retrievedBlogEntry.getId());

    }
}
