package communityblogger.services;


import communityblogger.domain.BlogEntry;
import communityblogger.domain.User;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
    private Map<Long, BlogEntry> blogEntryMap = new HashMap<Long, BlogEntry>();


    public BloggerResourceImpl() {
        // TO DO:
        // Initialise instance variables.
        initialiseContent();

    }

    public final void initialiseContent() {
        // TO DO:
        // (Re)-initialise data structures so that the Web service's state is
        // the same same as when the Web service was initially created.

        userHashMap.put("Bertmern", new User("Bertmern", "Brerce", "Werne"));
        userHashMap.put("Spodermern", new User("Spodermern", "Terby", "Mergwer"));
        //blogEntryMap.put("Test Blog Entry", new BlogEntry("XML Here"));
    }

    @Override
    public Response createUser(User user) {

        //if username does not exist (need to figure out how to check if a user exists)
        if (!userHashMap.containsKey(user.getUsername())) {

            userHashMap.put(user.getUsername(), user);

            return Response.status(201).link("/services/resources/retrieveUser/"
                    + user.getUsername(), "resource").build();

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

//    @Override
//    public Response createBlogEntry(BlogEntry blogContent) {
//
//            //create the user
//            BlogEntry createdBlogEntry = new BlogEntry("XML Pliz");
//        createdBlogEntry.setId(_idCounter.getAndIncrement());
//            blogEntryMap.put(blogContent);
//
//            return Response.status(201).link("services/resources/"
//                    + blogContent., "Newly Created User URI").build();
//
//    }

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
