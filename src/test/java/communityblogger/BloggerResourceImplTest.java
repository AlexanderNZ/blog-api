package communityblogger;

import communityblogger.domain.User;
import communityblogger.services.BloggerResource;
import communityblogger.services.BloggerResourceImpl;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.core.Response;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by alexcorkin on 10/09/2015.
 */

public class BloggerResourceImplTest {

//    /**
//     * Test that successfully creates a user
//     * @throws Exception
//     */
//    @org.junit.Test
//    public void createUserSuccess() throws Exception {
//
//        BloggerResource bloggerResource = new BloggerResourceImpl();
//        Response createdUser = bloggerResource.createUser("createdUser", "created_", "user");
//        assertEquals(createdUser, bloggerResource.retrieveUser("createdUser"));
//        assertEquals(201, createdUser.getStatus());
//    }

//    /**
//     * Test that fails to create a user
//     * @throws Exception
//     */
//    @org.junit.Test
//    public void createUserFailure() throws Exception {
//
//        BloggerResource bloggerResource = new BloggerResourceImpl();
//
//    }

//    /**
//     * Test that fails to creates a user
//     * @throws Exception
//     */
//    @org.junit.Test
//    public void createUserFail() throws Exception {
//
//        BloggerResource bloggerResource = new BloggerResourceImpl();
//
//
//    }

    /**
     * Retrieves a user that does not exist in the project, tests that a 404 is returned.
     * @throws Exception
     */
    @org.junit.Test
    public void retrieveNonExistentUser() throws Exception {

        BloggerResource bloggerResource = new BloggerResourceImpl();

        Response testUser = bloggerResource.retrieveUser("joe");

        assertNull(testUser.getEntity());

        assertEquals(404, testUser.getStatus());


    }

    /**
     * Retrieves a user that exists, tests that 200 and the user is returned
     * @throws Exception
     */
    @org.junit.Test
    public void retrieveUser() throws Exception {

        BloggerResource bloggerResource = new BloggerResourceImpl();

        Response response = bloggerResource.retrieveUser("extantUser");
        User user = (User) response.getEntity();

        assertEquals(200, response.getStatus());
        assertEquals("extantUser", user.getUsername());

    }

    /**
     * Retrieves a user that exists, tests that 200 and the user is returned
     * @throws Exception
     */
    @org.junit.Test
    public void retrieveUserIntegration() throws Exception {

        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://0.0.0.0:10000/services/");
        BloggerResource simple = target.proxy(BloggerResource.class);

        Response response = simple.retrieveUser("extantUser");
        User user = response.readEntity(User.class);

        assertEquals(200, response.getStatus());
        assertEquals("extantUser", user.getUsername());

    }
}