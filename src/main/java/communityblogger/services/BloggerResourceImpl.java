package communityblogger.services;


import com.sun.xml.bind.v2.TODO;
import communityblogger.domain.BlogEntry;
import communityblogger.domain.User;

import javax.ws.rs.core.Response;
import java.util.HashMap;
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
    private Map<String, User> userHashMap = new HashMap<String, User>();


    public BloggerResourceImpl() {
        // TO DO:
        // Initialise instance variables.

    }

    public void initialiseContent() {
        // TO DO:
        // (Re)-initialise data structures so that the Web service's state is
        // the same same as when the Web service was initially created.

        userHashMap.put("Bertmern", new User("Bertmern", "Brerce", "Werne"));
        userHashMap.put("Spodermern", new User("Spodermern", "Terby", "Mergwer"));
    }

    @Override
    public Response createUser(String username, String lastname, String firstname) {

        //if username does not exist (need to figure out how to check if a user exists)
        if (!userHashMap.containsKey(username)) {

            //create the user
            User createdUser = new User(username, lastname, firstname);
            userHashMap.put(username, createdUser);

            //TODO - Don't forget to add a link header (.link()) to this return
            return Response.status(201).entity(createdUser).build();

        } else
            return Response.status(409).build();

    }

    @Override
    public Response retrieveUser(String username) {

        //if user exists, return user
        if (userHashMap.containsKey(username))

            //need to store this into an xml response body
            return Response.status(200).entity(userHashMap.get(username)).build();

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
