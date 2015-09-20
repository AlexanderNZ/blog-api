package communityblogger.services;


import communityblogger.domain.BlogEntry;
import communityblogger.domain.Comment;
import communityblogger.domain.User;
import org.apache.log4j.BasicConfigurator;
import org.joda.time.DateTime;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.*;
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

        BasicConfigurator.configure();

        _idCounter = new AtomicLong(0);
        userHashMap.put("Bertmern", new User("Bertmern", "Brerce", "Werne"));
        userHashMap.put("Spodermern", new User("Spodermern", "Terby", "Mergwer"));

        //set up a test blog entry
        BlogEntry testBlogEntry = new BlogEntry("Here is item 0 in blogEntryMap");
        testBlogEntry.setId(_idCounter.getAndIncrement());
        testBlogEntry.setTimePosted(DateTime.now());
        User testBlogCreator = userHashMap.get("Spodermern");
        testBlogCreator.addBlogEntry(testBlogEntry);
        Comment testComment = new Comment("I'm a test comment", DateTime.now());
        testBlogEntry.addComment(testComment);
        blogEntryMap.put(0l, testBlogEntry);

        //to prove sets of blog entries, make another blog entry

        BlogEntry testBlogEntry2 = new BlogEntry("Here is item 1 - second item - pls werk");
        testBlogEntry2.setId(_idCounter.getAndIncrement());
        testBlogEntry2.setTimePosted(DateTime.now());
        User testBlogCreator2 = userHashMap.get("Bertmern");
        testBlogCreator2.addBlogEntry(testBlogEntry2);
        Comment testComment2 = new Comment("Plz comment moar", DateTime.now());
        testBlogEntry2.addComment(testComment2);
        blogEntryMap.put(1l, testBlogEntry2);
    }

    @Override
    public Response createUser(User user) {

        //if username does not exist (need to figure out how to check if a user exists)
        if (!userHashMap.containsKey(user.getUsername())) {

            userHashMap.put(user.getUsername(), user);

            return Response.status(201).link("/services/resources/user/"
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

    @Override
    public Response createBlogEntry(BlogEntry blogEntry, String username) {


        if (userHashMap.containsKey(username)) {
            User blogAuthor = userHashMap.get(username);
            //then user exists and request should succeed
            blogEntry.setId(_idCounter.getAndIncrement());
            blogEntry.setTimePosted(DateTime.now());
            blogAuthor.addBlogEntry(blogEntry);
            blogEntryMap.put(blogEntry.getId(), blogEntry);

            return Response.status(201).link("/services/resources/blog/"
                    + blogEntry.getId(), "resource").build();

        } else {
            //user does not exist and request should fail
            return Response.status(412).build();
        }

    }

    @Override
    public Response retrieveBlogEntry(String blogId) {

        long blogIdlong = Long.parseLong(blogId);
        //if blog entry exists, return blog entry
        if (blogEntryMap.containsKey(blogIdlong)) {

            //need to store this into an xml response body
            return Response.status(200).entity(blogEntryMap.get(blogIdlong)).build();

            //else return 404
        } else {
            return Response.status(404).build();
        }

    }

    @Override
    public Response createComment() {

        Response response = null;
        return response;
    }


    @Override
    public Response retrieveComments(String blogId) {

        //pull out the blogEntry we need to get comments from
        long blogIDLong = Long.parseLong(blogId);
        BlogEntry blogEntryForComments = blogEntryMap.get(blogIDLong);

        if (blogEntryMap.containsKey(blogIDLong)){
            //pull out the comments associated with that entry
            List<Comment> commentList = new ArrayList<Comment>(blogEntryForComments.getComments());

            //transform to GenericEntityList for the purposes of marshalling
            GenericEntity<List<Comment>> listGenericEntity = new GenericEntity<List<Comment>>(commentList) {};

            return Response.status(200).entity(listGenericEntity).build();
        } else {
            return Response.status(404).build();
        }
    }

    @Override
    public Response retrieveBlogEntries() {

        List<BlogEntry> blogEntries = new ArrayList<BlogEntry>(blogEntryMap.values());

        GenericEntity<List<BlogEntry>> entity = new GenericEntity<List<BlogEntry>>(blogEntries) {};

        return Response.status(200).entity(entity).build();
    }

    @Override
    public void followBlogEntry() {

    }
}
