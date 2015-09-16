package communityblogger.services;


import communityblogger.domain.BlogEntry;
import communityblogger.domain.User;

import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementation of the BloggerResource interface.
 */
public class BloggerResourceImpl implements BloggerResource {

    /*
     * Possible data structures to store the domain model objects.
     * _users is a map whose key is username. Each User is assumed to have a
     * unique username.
     * _blogEntries is a map whose key is the ID of a BlogEntry. Each BlogEntry
     * is assumed to have a unique ID.
     * _idCounter is a thread-safe counter, which can be used to assign unique
     * IDs to blogEntry objects as they are created.
     */
    private Map<String, User> _users;
    private Map<Long, BlogEntry> _blogEntries;
    private AtomicLong _idCounter;


    public BloggerResourceImpl() {
        // TO DO:
        // Initialise instance variables.
    }

    public void initialiseContent() {
        // TO DO:
        // (Re)-initialise data structures so that the Web service's state is
        // the same same as when the Web service was initially created.
    }

    @Override
    public Response createUser(String username, String lastname, String firstname) {

        User createdUser = new User(username, lastname, firstname);
        System.out.println("User Operation: Created user " + createdUser.getUsername());
        return Response.status(201).entity(createdUser).build();

    }

    @Override
    public Response retrieveUser(String username) {

        User returnedUser = new User("extantUser", "surname", "firstname");

        System.out.println("user: " + returnedUser.getUsername() + " created.");
        //if user exists, return user
        if (username.equals(returnedUser.getUsername()))

            //need to store this into an xml response body
            return Response.status(200).entity(returnedUser).build();

            //else return 404
        else {
            return Response.status(404).build();
        }


    }

    @Override
    public void createBlogEntry() {

    }

    @Override
    public void retrieveBlogEntry() {

    }

    @Override
    public void createComment() {

    }

    @Override
    public void retrieveComments() {

    }

    @Override
    public void retrieveBlogEntries() {

    }

    @Override
    public void followBlogEntry() {

    }
}
